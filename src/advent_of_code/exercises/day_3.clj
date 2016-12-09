(ns advent-of-code.exercises.day-3
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day3.txt")))

(defn is-triangle? [side-lengths]
  (let [[first second third]           side-lengths]
    (and
     (< first (+ second third))
     (< second (+ third first))
     (< third (+ first second)))))

(defn format-triangle-vector [string]
  (mapv
     read-string
     (string/split
        (string/trim string)
        #"[\s]+")))

(defn part-1 []
  (let [triangle-list         (vec (map
                                    format-triangle-vector
                                    (string/split-lines data)))
        triangles             (frequencies (map is-triangle? triangle-list))]
    (println "Number of triangles: " (get triangles true))
    (println "Number of lieangles: " (get triangles false))))

(defn reformat [partitioned-list]
  (for [x [0 1 2]]
     [
      (-> partitioned-list (nth 0) (nth x))
      (-> partitioned-list (nth 1) (nth x))
      (-> partitioned-list (nth 2) (nth x))]))

(defn part-2 []
  (let [unformatted           (vec (map
                                    format-triangle-vector
                                    (string/split-lines data)))
        partitioned           (partition-all 3 unformatted)
        rearranged            (reduce into (map reformat partitioned))
        triangles             (frequencies (map is-triangle? rearranged))]
      (println "Number of triangles (reformatted): " (get triangles true))
      (println "Number of lieangles (reformatted): " (get triangles false))))

(defn run []
  (println "_________________DAY 3: SQUARES WITH THREE SIDES_________________")
  (part-1)
  (part-2))
