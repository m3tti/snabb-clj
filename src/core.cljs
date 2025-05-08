(ns core
  (:require
   ["snabbdom" :as s]))

(defonce current-vnode (atom nil))
(defonce render-function (atom nil))
(defonce state (atom #js {:text "Hello"}))
(defonce patch (s/init [s/styleModule s/propsModule
                        s/eventListenersModule s/classModule]))

(defn render
  ([]
   (let [old-vnode @current-vnode]
     (reset! current-vnode (@render-function))
     (patch old-vnode @current-vnode)))
  ([el render-fn]
   (reset! render-function render-fn)
   (reset! current-vnode (render-fn))
   (patch el @current-vnode)))

(defn effect [fun]
  (-> (fun @state)
      (.then #(reset! state #js %))
      (.finally
       #(render))))

(defn dispatch [fun]
  (reset! state (fun @state))
  (render))
