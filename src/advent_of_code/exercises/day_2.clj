(ns advent-of-code.exercises.day-2
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day2.txt")))

(def keyboard [[1 2 3] [4 5 6] [7 8 9]])

;finger position is tracked based on distance from the '5' key
(def finger-position (atom {:horizontal-displacement 1 :vertical-displacement 1}))

(defn move [movement]
  (case movement
    "U" (swap! finger-position update-in [:vertical-displacement] dec)
    "D" (swap! finger-position update-in [:vertical-displacement] inc)
    "L" (swap! finger-position update-in [:horizontal-displacement] dec)
    "R" (swap! finger-position update-in [:horizontal-displacement] inc)))

(defn can-move? [movement]
  (case movement
    "U" (> (:vertical-displacement @finger-position) 0)
    "D" (< (:vertical-displacement @finger-position) 2)
    "L" (> (:horizontal-displacement @finger-position) 0)
    "R" (< (:horizontal-displacement @finger-position) 2)))

(defn get-digit [sequence]
  (let [single-digit-sequence     (string/split sequence #"")]
    (doseq [movement single-digit-sequence]
      (if (can-move? movement)
        (move movement)))
    ; (println (get @finger-position :vertical-displacement))))
    (nth
     (nth keyboard
          (get @finger-position :vertical-displacement))
     (get @finger-position :horizontal-displacement))))


(defn run []
  (let [sequences    (string/split-lines data)
        keycode      (map get-digit sequences)]
    (println keycode)))
