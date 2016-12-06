(ns advent-of-code.exercises.1-no-time-for-a-taxicab
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day1.txt")))

(def any? (complement not-any?))

;atom keeps track of direction you are facing and how far horizontally or vertically you have travelled
(def navigation-atom
  (atom {:direction 0 :vertical-displacement 0 :horizontal-displacement 0}))

(def location-tracker-atom (atom []))

(def cardinal-directions ["N" "E" "S" "W"])

(defn step [x]
  (let [orientation   (str (first x))
        distance      (read-string (string/join (rest x)))]
    (if (= orientation "R")
      (swap! navigation-atom update-in [:direction] inc)
      (swap! navigation-atom update-in [:direction] dec))
    (let [direction     (nth
                           cardinal-directions
                           (mod (get @navigation-atom :direction) 4))]
      (cond
        (= direction "N")     (swap! navigation-atom update-in [:vertical-displacement] + distance)
        (= direction "S")     (swap! navigation-atom update-in [:vertical-displacement] - distance)
        (= direction "E")     (swap! navigation-atom update-in [:horizontal-displacement] + distance)
        (= direction "W")     (swap! navigation-atom update-in [:horizontal-displacement] - distance)))))


(defn part-1 []
  (let [vector-path (string/split data #", ")]
    (doseq [x vector-path]
      (step x))
    (println (+ (Math/abs (get @navigation-atom :horizontal-displacement))
                (Math/abs (get @navigation-atom :vertical-displacement))))))


(defn run []
  (part-1))
