(ns advent-of-code.exercises.day-4
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day4.txt")))

(defn decode-encryption [encryption]
  (let [split-encryption    (string/split  encryption #"-")
        encryption-code     (frequencies
                              (-> split-encryption butlast string/join))
        [num checksum]      (string/split
                              (string/replace (last split-encryption) #"\]" "")
                              #"\[")]
      (vector encryption-code num checksum)))

(defn valid-char? [encryption-code checksum]
  (let [sorted-numeric-encryption  (sort-by val encryption-code)
        [last-letter last-count]  (last sorted-numeric-encryption)
        [first-count-letter first-count-number]  (first (filter #(= (nth % 1) last-count) sorted-numeric-encryption))]
    (or
      (= last-letter (first checksum))
      (= first-count-letter (first checksum)))))


(defn valid-item-in-sequence? [encryption-code checksum]
    (if (empty? checksum)
      true
      (if (valid-char? encryption-code checksum)
        (recur (dissoc encryption-code (first checksum)) (rest checksum))
        false)))

(defn sum-if-valid [[encryption-code num checksum]]
  (if (valid-item-in-sequence? encryption-code checksum)
    (read-string num)
    0))


(defn run []
  (println "_________________DAY 4: Security Through Obscurity_________________")
  (let [sequences               (string/split-lines data)
        passcodes               (map decode-encryption sequences)]
      (println (reduce + (map sum-if-valid passcodes)))))
