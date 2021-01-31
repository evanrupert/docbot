(ns docbot.core
  (:require [docbot.slack.listener :refer [start-app]]
            [docbot.slack.sender :refer [post-reply]]
            [docbot.parser :as parser]
            [docbot.meta :refer [get-meta]]
            [docbot.format :as format]
            [clojure.string :as string]))

(defn- parse-and-extract-function-names
  [code-snippet]
  (->> code-snippet
       format/replace-escaped-characters
       read-string
       parser/extract-function-names
       distinct))

(defn- format-docs-into-string
  [function-list]
  (->> function-list
       (map (comp format/format-function-data get-meta))
       (string/join "\n\n")))

(defn send-function-docs
  [channel thread code-snippet]
  (let [function-list (parse-and-extract-function-names code-snippet)]
    (when (not (empty? function-list))
      (post-reply channel thread (format-docs-into-string function-list)))))

(defn event-handler
  [event]
  (let [code-snippet (parser/extract-code (:text event))]
    (when code-snippet
      (send-function-docs (:channel event) (:ts event) code-snippet))))

(defn command-handler
  [command-data]
  (let [code-snippet  (str "(" (get command-data "text") ")")
        function-list (parse-and-extract-function-names code-snippet)]
    (if (not (empty? function-list))
      (format-docs-into-string function-list)
      "That is not a valid function name")))

(defn -main
  [& args]
  (start-app event-handler command-handler))
