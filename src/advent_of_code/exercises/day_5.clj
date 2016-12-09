(ns advent-of-code.exercises.day-5
  (:require [clojure.string :as string])
  (:import (java.security MessageDigest)
           (java.math BigInteger)
           (java.lang Character)))

(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(defn valid? [hashed-door-id password]
  (let [all-zeroes?    (every?
                           #(zero? (Character/getNumericValue %))
                           (take 5 hashed-door-id))
        valid-index?   (> 8 (Character/getNumericValue (nth hashed-door-id 5)))
        not-filled?    (nil? (get password (Character/getNumericValue (nth hashed-door-id 5))))]
    (and all-zeroes? valid-index? not-filled?)))

(defn get-password [door-id index password]
  (if (= 8 (count password))
    password
    (let [door-id-indexed    (str door-id (str index))
          hashed-door-id     (md5 door-id-indexed)]
      (if
        (valid? hashed-door-id password)
        (recur door-id (inc index) (assoc password
                                          (Character/getNumericValue (nth hashed-door-id 5))
                                          (nth hashed-door-id 6)))
        (recur door-id (inc index) password)))))

(defn run []
  (println "_________________DAY 5: How About a Nice Game of Chess?_________________"
    (let [password        (get-password "ojvtpuvg" 0 {})]
      (println password)
      (println (into (sorted-map) password))
      (println (vals (into (sorted-map) password))))))
