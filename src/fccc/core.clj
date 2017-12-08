(ns fccc.core)

(defn n-primes [n]
  (when (pos? n)
    (->> (iterate #(+ 2 %) 3)
         (reduce (fn [primes new]
                   (if (== n (count primes))
                     (reduced primes)
                     (cond-> primes
                             (every? #(ratio? (/ new %)) primes)
                             (conj new))))
                 [2]))))

