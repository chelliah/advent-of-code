(ns advent-of-code.exercises.day-2
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def data (slurp (io/resource "day2.txt")))

(def keyboard-1 [[1 2 3] [4 5 6] [7 8 9]])

(def keyboard-2 [[nil nil 1 nil nil] [nil 2 3 4 nil] [5 6 7 8 9] [nil "A" "B" "C" nil] [nil nil "D" nil nil]])

;finger position is tracked based on distance from the '5' key
(def finger-position (atom {:horizontal-displacement 1 :vertical-displacement 1}))

(defn get-number-by-finger-position [keyboard vertical-displacement horizontal-displacement]
  (nth
   (nth keyboard vertical-displacement nil)
   horizontal-displacement
   nil))

(defn move [movement]
  (case movement
    "U" (swap! finger-position update-in [:vertical-displacement] dec)
    "D" (swap! finger-position update-in [:vertical-displacement] inc)
    "L" (swap! finger-position update-in [:horizontal-displacement] dec)
    "R" (swap! finger-position update-in [:horizontal-displacement] inc)))

(defn can-move? [movement keyboard]
  (case movement
    "U" (not (nil?
              (get-number-by-finger-position
               keyboard
               (dec (get @finger-position :vertical-displacement))
               (get @finger-position :horizontal-displacement))))
     "D" (not (nil?
               (get-number-by-finger-position
                keyboard
                (inc (get @finger-position :vertical-displacement))
                (get @finger-position :horizontal-displacement))))
      "L" (not (nil?
                (get-number-by-finger-position
                 keyboard
                 (get @finger-position :vertical-displacement)
                 (dec (get @finger-position :horizontal-displacement)))))
       "R" (not (nil?
                 (get-number-by-finger-position
                  keyboard
                  (get @finger-position :vertical-displacement)
                  (inc (get @finger-position :horizontal-displacement)))))))

; (defn can-move? [movement]
;   (case movement
;     "U" (> (:vertical-displacement @finger-position) 0)
;     "D" (< (:vertical-displacement @finger-position) 2)
;     "L" (> (:horizontal-displacement @finger-position) 0)
;     "R" (< (:horizontal-displacement @finger-position) 2)))

(defn get-digit [sequence keyboard]
  (let [single-digit-sequence     (string/split sequence #"")]
    (doseq [movement single-digit-sequence]
      (if (can-move? movement keyboard)
         (move movement)))
    (get-number-by-finger-position
      keyboard
      (get @finger-position :vertical-displacement)
      (get @finger-position :horizontal-displacement))))


(defn run []
  (println "_________________DAY 2: BATHROOM SECURITY_________________")
  (let [sequences    (string/split-lines data)
        keycode-1    (map #(get-digit % keyboard-1) sequences)
        _            (reset! finger-position {:horizontal-displacement 0 :vertical-displacement 2})
        keycode-2    (map #(get-digit % keyboard-2) sequences)]
    (println "Code for keypad 1: " keycode-1)
    (println "Code for keypad 2: "keycode-2)))
