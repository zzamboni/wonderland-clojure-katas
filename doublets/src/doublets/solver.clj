(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn replace-chr [s i c]
  (apply str (map-indexed #(if (= %1 i) c %2) s)))

(defn next-words [w]
  (remove #{w} (filter (set words)
                       (for [i (range (count w)) c (range 25)]
                         (replace-chr w i (char (+ c (int \a))))))))

(defn next-sets [lw] (map #(concat lw [%]) (remove (set lw) (next-words (last lw)))))

(defn doublets [word1 word2]
  (or (first (filter #(= word2 (last %))
                     (apply concat
                            (take-while not-empty
                                        (iterate #(mapcat next-sets %) [[word1]])))))
      []))
