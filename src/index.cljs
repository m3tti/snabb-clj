(ns App
  (:require
   ["snabbdom" :as s :refer [h]]
   [core :as c]))

(defn ^:async something-async [s]
  (do
    (js-await (new js/Promise #(js/setTimeout %  1000)))
    (console.log "Done waiting")
    (js/Promise.resolve {:text "resolved"})))

(defn App []
  (h :div
     (h :div
        [(h :code (js/JSON.stringify @c/state))
         (h :a {:on {:click #(c/dispatch (fn [s] (merge s {:text "sync"})))}}
            "Sync Effect")
         (h :a {:on {:click #(c/effect! something-async)}}
            "ASync Effect")])))

(c/render (js/document.getElementById "app")
        App)
