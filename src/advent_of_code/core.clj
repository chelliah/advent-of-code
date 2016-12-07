(ns advent-of-code.core
  (:gen-class)
  (:require [advent-of-code.exercises.day-1 :as one]
            [advent-of-code.exercises.day-2 :as two]))

(defn -main
  [& args]
  (two/run))
