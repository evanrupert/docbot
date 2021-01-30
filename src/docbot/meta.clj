(ns docbot.meta)

(defmacro functionalize
  "Wrap a function around the given macro, allowing it to be used as a function"
  [macro]
  `(fn [& args#] (eval (cons '~macro args#))))

(defn get-meta
  "Return meta information for the given function"
  [function]
  (meta ((functionalize var) function)))
