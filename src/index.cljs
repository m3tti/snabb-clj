(ns App
  (:require
   ["snabbdom" :as s :refer [h]]))

(defonce current-vnode (atom nil))
(defonce state (atom #js {}))
(defonce patch (s/init [s/styleModule s/propsModule
                        s/eventListenersModule s/classModule]))

(defn render [el new-vnode]
  (reset! current-vnode new-vnode)
  (patch el new-vnode))

(defn dispatch []
  )

(defn App []
  (h :div.reveal
     (h :div.slides
        [(h :section "Hello")
         (h :section "World")])))

(render (js/document.getElementById "app")
        (App))
