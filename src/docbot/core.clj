(ns docbot.core
  (:require [docbot.slack.listener :refer [start-app]]
            [docbot.slack.sender :refer [post-reply]]
            [docbot.parser :as parser]
            [docbot.meta :refer [get-meta]]
            [docbot.format :as format]
            [clojure.string :as string]))

(def test-code
  (str "(defn- bot?\n"
       "  [req]\n"
       "  (not (nil? (get-in req [:body :event :bot_id]))))\n"))

(defn send-function-docs
  [channel thread code-snippet]
  (->> code-snippet
       parser/replace-escaped-characters
       read-string
       parser/extract-function-names
       (map (comp format/format-function-data get-meta))
       (string/join "\n\n")
       (post-reply channel thread)))

(defn event-handler
  [event]
  (let [code-snippet (parser/extract-code (:text event))]
    (when code-snippet
      (send-function-docs (:channel event) (:ts event) code-snippet))))

; TODO: Do not post if there are no functions to define
; TODO: Post replies to post with docs and link to doc website
; TODO:   if post is OG then post in replies
; TODO:   if post itself is reply, post beneath it

; Streach
; TODO: Support single-line code snippets
; TODO: Ignore non-clojure code snippets without error
; TODO: Also provide documentation for macros

; Notes
; determine if code block is clojure code by attempting a
; read-string and seeing if it fails

(defn -main
  [& args]
  (start-app event-handler))
