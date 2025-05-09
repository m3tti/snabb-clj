# snabb-clj
Small framework for SPAs

## CLJS

``` clojure
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
         (h :a {:on {:click #(c/effect something-async)}}
            "ASync Effect")])))

(c/render (js/document.getElementById "app")
        App)
```

## JS
``` javascript
import { h } from "snabbdom";
import { dispatch, effect, render, getState } from "./lib.js";

const somethingAsync = s =>
      new Promise(resolve => setTimeout(() => resolve({text: "async"}), 1000));

const App = () =>
      h("div", [
          h("code", JSON.stringify(getState())),
          h("a", {on: {click: () => dispatch(s => ({...s, text: "hello"}))}}, "sync"),
          h("a", {on: {click: () => effect(somethingAsync)}}, "async")
      ]);

render(document.getElementById("app"),
       App);
```
