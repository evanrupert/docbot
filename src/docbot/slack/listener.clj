(ns docbot.slack.listener
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-body]]))

(defn challenge?
  [req]
  (contains? (:body req) :challenge))

(defn accept-challenge
  [req]
  {:status       200
   :content-type "text/plain"
   :body         (get-in req [:body :challenge])})

(defn bot?
  [req]
  (not (nil? (get-in req [:body :event :bot_id]))))

(defn handle-requests
  [event-handler]
  (fn [req]
    (cond
      (challenge? req) (accept-challenge req)
      (bot? req) {:status 200}
      :else (do
              (event-handler (get-in req [:body :event]))
              {:status 200}))))

(defn start-app
  [event-handler]
  (-> event-handler
      handle-requests
      (wrap-json-body {:keywords? true})
      (run-jetty {:port 3000})))

;(defn wrap-challenge
;  [event-handler]
;  (fn [req]
;    (if (challenge? req)
;      (accept-challenge req)
;      (do
;        (if (not (bot? req))
;          (event-handler req))
;        {:status 200}))))
