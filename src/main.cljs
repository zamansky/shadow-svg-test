(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]
            ))


(defn svg []
  [:svg {:width "100" :height "100"}
   [:circle {:cx "50" :cy "50" :r "30" :stroke "green" :stroke-width "4"
             :fill "yellow"}]
   ])
(defn main-component []
  [:div 
   [:h1 "This is a component"]
   (svg)])


(defn mount [c]
(r/render-component [c] (.getElementById js/document "app"))
)

(defn reload! []
(mount main-component)
(print "Hello reload!"))

(defn main! []
(mount main-component)
(print "Hello Main"))
