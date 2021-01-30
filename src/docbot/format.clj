(ns docbot.format
  (:require [clojure.string :as string]))

(defn replace-escaped-characters
  "Revert the the automatically inserted slack escape codes back to their original characters"
  [text]
  (-> text
      (string/replace "&lt;" "<")
      (string/replace "&gt;" ">")
      (string/replace "&amp;" "&")))

(defn escape-reserved-characters
  "Replace <, >, & with their escape counterparts"
  [text]
  (-> text
      (string/replace "<" "&lt;")
      (string/replace ">" "&gt;")
      (string/replace "&" "&amp;")))

(def ^:private clojure-docs-base-url
  "https://clojuredocs.org/")

(defn- escape-link-characters
  [text]
  (string/replace text "?" "_q"))

(defn- build-link-url
  [namespace name]
  (str clojure-docs-base-url
       (.toString namespace)
       "/"
       (escape-link-characters name)))

(defn format-function-data
  [{name      :name
    arglists  :arglists
    doc       :doc
    namespace :ns}]
  (println (.toString namespace))
  (let [quoted-doc   (string/replace doc "\n" "\n> ")
        link-url     (build-link-url namespace name)]
    (str "<" link-url "|*" name "*" ">\n"
         "`" arglists "`" "\n"
         "> " quoted-doc "\n")))
