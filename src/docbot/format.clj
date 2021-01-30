(ns docbot.format
  (:require [clojure.string :as string]))

(defn format-function-data
  [{name     :name
    arglists :arglists
    doc      :doc}]
  (println doc)
  (let [quoted-doc (string/replace doc "\n" "\n> ")]
    (str "*" name "*" "\n"
         "`" arglists "`" "\n"
         "> " quoted-doc "\n")))
