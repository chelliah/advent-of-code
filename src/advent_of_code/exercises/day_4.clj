(ns advent-of-code.exercises.day-4
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day4.txt")))

(defn decode-encryption [encryption]
  (let [split-encryption      (string/split  encryption #"-")
        encryption            (-> split-encryption butlast string/join)
        encryption-sorted     (frequencies
                                (-> split-encryption butlast string/join))
        [num checksum]         (string/split
                                  (string/replace (last split-encryption) #"\]" "")
                                  #"\[")]
      (vector encryption-sorted num checksum encryption)))

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

(defn sum-if-valid [[encryption-code num checksum encryption]]
  (if (valid-item-in-sequence? encryption-code checksum)
    (read-string num)
    0))

(defn return-if-valid [[encryption-code num checksum encryption]]
  (if (valid-item-in-sequence? encryption-code checksum)
    [encryption num]))

(defn convert-letter [letter num]
  (let [alphabet        "abcdefghijklmnopqrstuvwxyz"
        starting-index  (string/index-of alphabet letter)
        movement        (mod (read-string num) 26)]
     (nth alphabet (mod (+ starting-index movement) 26))))

(defn decode [[encryption-code num]]
  {
    (keyword (string/join (map #(convert-letter % num) encryption-code)))
    num})

(defn run []
  (println "_________________DAY 4: Security Through Obscurity_________________")
  (let [sequences               (string/split-lines data)
        passcodes               (map decode-encryption sequences)
        valid-passcodes         (remove nil? (map return-if-valid passcodes))
        decoded-names           (sort-by str (map decode valid-passcodes))]
    (println decoded-names)))
    ; (println (get decoded-names :northpoleobjectstorage))))
