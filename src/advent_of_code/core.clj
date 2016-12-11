(ns advent-of-code.core
  (:gen-class)
  (:require [advent-of-code.exercises.day-1 :as one]
            [advent-of-code.exercises.day-2 :as two]
            [advent-of-code.exercises.day-3 :as three]
            [advent-of-code.exercises.day-4 :as four]
            [advent-of-code.exercises.day-5 :as five]
            [advent-of-code.exercises.day-6 :as six]))
(defn -main
  [& args]
  (six/run))
