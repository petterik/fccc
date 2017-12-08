(ns fccc.core)

(defn n-primes [n]
  (when (pos? n)
    (->> (iterate (comp inc inc) 3)
         (filter (fn [new]
                   (every? #(ratio? (/ new %)) (range 3 new))))
         (cons 2)
         (take n))))

