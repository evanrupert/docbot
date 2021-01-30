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

(defn extract-function-names
  [code]
  (->> code
       flatten
       (filter test/function?)))
