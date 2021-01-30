(ns docbot.slack.sending
  (:require [clj-http.client :as http]))

(def api-key
  (System/getenv "DOCBOT_SLACK_API_KEY"))

(def base-url
  "https://slack.com/api")

(defn post
  [url body]
  (http/post url
             {:headers      {"Authorization" (str "Bearer " api-key)}
              :content-type :json
              :accept       :json
              :form-params  body}))

(defn post-message
  [channel msg]
  (post (str base-url "/chat.postMessage")
        {:channel channel
         :text    msg})
  {:status 200})
