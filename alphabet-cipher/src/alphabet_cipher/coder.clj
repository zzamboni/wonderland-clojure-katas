(ns alphabet-cipher.coder)

(defn encode [keyword message]
  (apply str (map (fn [m k] (char (+ (int \a) (mod (+ (int k) (int m) (- (* 2 (int \a)))) 26))))
                  (seq message) (cycle (seq keyword)))))

(defn decode [keyword message]
  (apply str (map (fn [m k] (char (+ (int \a) (mod (- (int m) (int k)) 26))))
                  (seq message) (cycle (seq keyword)))))

(defn decipher [cipher message]
  (let [fullkey (apply str (map (fn [c m] (char (+ (int \a) (mod (- (int c) (int m)) 26))))
                                (seq cipher) (seq message)))
        l (count fullkey)] 
    (->> (range 1 (inc l))
         (map #(subs fullkey 0 %))
         (filter #(= fullkey (->> % seq cycle (take l) (apply str))))
         first)))

