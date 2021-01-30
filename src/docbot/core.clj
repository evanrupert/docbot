(ns docbot.core
  (:require [docbot.slack.listener :refer [start-app]]
            [docbot.slack.sending :refer [post-message]]))

(defn event-handler
  [event]
  (post-message (:channel event) (:text event)))

(defn -main
  [& args]
  (start-app event-handler))
