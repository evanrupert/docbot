(ns docbot.core
  (:require [docbot.slack.listener :refer [start-app]]
            [docbot.slack.sending :refer [post-message]]))

(defn event-handler
  [event]
  (post-message (:channel event) (:text event)))

; TODO: Implement regex to determine if snippet has code in it
; TODO: Maybe determine if code is clojure?
; TODO: Extract functions from clojure code
; TODO: Post replies to post with docs and link to doc website
; TODO:   if post is OG then post in replies
; TODO:   if post itself is reply, post beneath it

; Notes
; determine if code block is clojure code by attempting a
; read-string and seeing if it fails

(defn -main
  [& args]
  (start-app event-handler))
