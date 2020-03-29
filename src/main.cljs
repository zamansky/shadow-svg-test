(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]
            
            ))



(def c [:circle {:id "1" :cx "150" :cy "50" :r "30" :stroke "green" :stroke-width "4"
                 :fill "red"}])



(defonce state (r/atom {}))

(defn movetest [e]
  (let [id (keyword (-> (-> e .-target) .-id) )
        newx (-  (-> e .-pageX) 10)
        newy (-  (-> e .-pageY)10)
        ]
    (js/console.log   @state)
    (swap! state assoc-in [id 1 :cx]  (str newx) )
    (swap! state assoc-in [id 1 :cy] (str  newy))
    
    ))

(def init-state {:2 [:circle {:on-mouseMove movetest
                              
                              
                              :id "2" :cx "50" :cy "50" :r "20" :stroke "green" :stroke-width "4"}]})

  (reset! state init-state)
  
(defn mount [c]
  (r/render-component [c] (.getElementById js/document "app"))
  )

(defn svg []
  [:svg
   
   ;;{:on-mouseMove (fn [e] (js/console.log e)) :width "200" :height "200"}
   {:width "200" :height "200"}
   (for [ [k c] @state]
     c)
   ])
(defn main-component []

[:div 
 [:h1 "This is a component"]
 ( svg)
 [:button {:on-click (fn [] (swap! state conj c))}"HELLO" ]
 ])

(defn reload! []
  (mount  main-component)
  (print "Hello reload!"))

(defn main! []
  (mount main-component)
  (print "Hello Main"))

