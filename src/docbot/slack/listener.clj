(ns docbot.slack.listener
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.json :refer [wrap-json-body]]
            [cheshire.core :refer [generate-string]]))

(defn- challenge?
  [req]
  (contains? (:body req) :challenge))

(defn- accept-challenge
  [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (get-in req [:body :challenge])})

(defn- bot?
  [req]
  (not (nil? (get-in req [:body :event :bot_id]))))

(defn- command?
  [req]
  (= "/command" (:uri req)))

(defn- handle-requests
  [event-handler command-handler]
  (fn [req]
    (println req)
    (cond
      (command? req)   (do
                         (println "Received Command Message, forwarding to command handler...")
                         (let [response-text (command-handler (:params req))]
                           {:status  200
                            :headers {"Content-Type" "application/json"}
                            :body    (generate-string
                                      {:response_type "in_channel"
                                       :text          response-text})}))
      (challenge? req) (do
                         (println "Received Challenge, accepting...")
                         (accept-challenge req))
      (bot? req)       (do
                         (println "Received Message From bot, no action...")
                         {:status 200})
      :else            (do
                         (println "Received Message from user, forwarding to event handler...")
                         (event-handler (get-in req [:body :event]))
                         {:status 200}))))

(defn start-app
  [event-handler command-handler]
  (println "Starting event listener...")
  (-> (handle-requests event-handler command-handler)
      (wrap-json-body {:keywords? true})
      wrap-params
      (run-jetty {:port 3000})))
