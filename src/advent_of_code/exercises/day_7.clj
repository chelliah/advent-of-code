(ns advent-of-code.exercises.day-7
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(def data (slurp (io/resource "day7.txt")))


(defn has-abba? [char-sequence]
  (let [substring     (take 4 char-sequence)]
    (if (> 4 (count substring))
      false
      (if (and
           (= substring (reverse substring))
           (not= (nth substring 0) (nth substring 1)))
        true
        (recur (rest char-sequence))))))

(defn supports-tls? [ip-address]
  (println ip-address)
  (println (and
              (not-any? has-abba? (get ip-address :hypernets))
              (some has-abba? (get ip-address :rest))))
  (and
   (not-any? has-abba? (get ip-address :hypernets))
   (some has-abba? (get ip-address :rest))))

(defn partition-list [ip-address]
  (let [all           (string/split ip-address #"(\[)|(\])")
        hypernets     (map #(string/replace % #"(\[)|(\])" "") (re-seq #"\[\w+\]" ip-address))]
    {:hypernets (set hypernets)
     :rest (set/difference (set all) (set hypernets))}))

(defn run []
  (println "_________________DAY 6: Signals and Noise_________________")
  (let [sequences              (map
                                #(partition-list %)
                                (string/split-lines data))]
      (println (frequencies (map supports-tls? sequences)))))
