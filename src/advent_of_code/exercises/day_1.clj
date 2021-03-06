(ns advent-of-code.exercises.day-1
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day1.txt")))

;atom keeps track of direction you are facing and how far horizontally or vertically you have travelled
(def navigation-atom
  (atom {:direction 0 :vertical-displacement 0 :horizontal-displacement 0}))

(def location-tracker-atom (atom []))

(def cardinal-directions ["N" "E" "S" "W"])

(defn move-user [direction]
  (cond
    (= direction "N")     (swap! navigation-atom update-in [:vertical-displacement] inc)
    (= direction "S")     (swap! navigation-atom update-in [:vertical-displacement] dec)
    (= direction "E")     (swap! navigation-atom update-in [:horizontal-displacement] inc)
    (= direction "W")     (swap! navigation-atom update-in [:horizontal-displacement] dec))
  (swap! location-tracker-atom conj (dissoc @navigation-atom :direction)))

(defn step [x]
  (let [orientation   (str (first x))
        distance      (read-string (string/join (rest x)))]
    (if (= orientation "R")
      (swap! navigation-atom update-in [:direction] inc)
      (swap! navigation-atom update-in [:direction] dec))
    (let [direction     (nth
                           cardinal-directions
                           (mod (get @navigation-atom :direction) 4))]
      (doseq [y (range 0 distance)]
        (move-user direction)))))


(defn find-overlap [previously-visited other-nodes]
  (let [current-node   (first other-nodes)
         visiting-next  (rest other-nodes)]
    (if
      (and
       (not (nil? current-node))
       (.contains previously-visited current-node))
      (println
       "Answer to Part Two:"
       (+ (Math/abs (get current-node :horizontal-displacement))
          (Math/abs (get current-node :vertical-displacement))))
      (recur (conj previously-visited current-node) visiting-next))))

(defn part-1 []
  (println
   "Answer to Part One:"
   (+ (Math/abs (get @navigation-atom :horizontal-displacement))
      (Math/abs (get @navigation-atom :vertical-displacement)))))

(defn part-2 []
  (find-overlap [(first @location-tracker-atom)] (rest @location-tracker-atom)))

(defn run []
  (println "_________________DAY 1: NO TIME FOR A TAXICAB_________________")
  (let [vector-path (string/split data #", ")]
    (doseq [x vector-path]
      (step x))
    (part-1)
    (part-2)))
