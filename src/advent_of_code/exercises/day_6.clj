(ns advent-of-code.exercises.day-6
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day6.txt")))

(defn reformat [sequence]
  (let [split (string/split sequence #"")]))

(defn run []
  (println "_________________DAY 6: Signals and Noise_________________")
  (let [sequences              (string/split-lines data)
        split                 (map #(string/split % #"") sequences)
        split-by-frequency
                              (for [x (range 8)]
                                (frequencies (map #(nth % x) split)))]
    (println "part 1"
        (map
          #(key (apply max-key val %))
          split-by-frequency))

    (println "part 2"
      (map
        #(key (apply min-key val %))
        split-by-frequency))))
