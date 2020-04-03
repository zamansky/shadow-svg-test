(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]
            
            ))
(enable-console-print!)


(def c [:circle {:id "1" :cx "150" :cy "50" :r "30" :stroke "green" :stroke-width "4"
                 :fill "red"}])

(defn make-circle [x y id]
  [:circle {            
            :id (str id) :cx x :cy y :r 15 :stroke "black" :stroke-width 1 :fill "red" :key (str id)
            :on-click (fn [e]  (prn "CIRCLE") (.stopPropagation e))}])

(defonce state (r/atom {}))

(defn movetest [e]
(let [id (keyword (-> (-> e .-target) .-id) )
      newx (-  (-> e .-pageX) 10)
      newy (-  (-> e .-pageY)10)
      ]

  ;;(js/console.log   @state)
  (swap! state assoc-in [:circles id 1 :cx]  (str newx) )
  (swap! state assoc-in [:circles id 1 :cy] (str  newy))
  ))

(def init-state {:next_id 1
                 :circles {}} )

(reset! state init-state)

(defn mount [c]
(r/render-component [c] (.getElementById js/document "app"))
)


(defn mysvg []
[:svg

 ;;{:on-mouseMove (fn [e] (js/console.log e)) :width "640" :height "480" :border 1}

 {:xmlns "http://www.w3.org/2000/svg"
  :on-click (fn [e]
              (let [
                    x (-> e .-clientX)
                    y (-> e .-clientY)
                    id (:next_id @state)
                    circles (:circles @state)
                    c (make-circle x y id)
                    ]
                (swap! state assoc  :next_id (inc id))
                (swap! state assoc :circles (assoc circles id c) )
                (prn "ONSVG")
                (.preventDefault e)
                false
                )
              )
  
  :width "640" :height "480"
  :viewBox="0 0 640 480"}
 (for [ [k c] (:circles @state)]
   c)
 ])
(defn main-component []

[:div 
 [:h1 "This is a component"]
 ( mysvg)
 [:button {:on-click (fn [] (swap! state conj c))}"HELLO" ]
 ])

(defn reload! []
(mount  main-component)
(print "Hello reload!"))

(defn main! []
(mount main-component)
(print "Hello Main"))

