(ns docbot.parser
  (:require [clojure.test :as test]
            [clojure.string :as string]))

(def ^:private code-snippet-regex
  #"(?ms).*```(.*)```.*")

(defn has-code-snippet?
  [text]
  (re-matches code-snippet-regex text))

(defn extract-code
  [text]
  (get (re-matches code-snippet-regex text) 1))

(defn replace-escaped-characters
  "Revert the the automatically inserted slack escape codes back to their original characters"
  [text]
  (-> text
       (string/replace "&lt;" "<")
       (string/replace "&gt;" ">")
       (string/replace "&amp;" "&")))

(defn extract-function-names
  [code]
  (->> code
       flatten
       (filter test/function?)))
