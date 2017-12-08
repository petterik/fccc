(ns fccc.core)

(defn prime? [primes i]
  (let [largest-factor (long (inc (Math/sqrt i)))]
    (->> primes
         ;; Only takes until the largest possible factor.
         (take-while #(<= % largest-factor))
         (every? #(ratio? (/ i %))))))

(defn n-primes [n]
  (when (pos? n)
    ;; Generates all odd numbers.
    (->> (iterate #(+ 2 %) 3)
         ;; Using reduce to have the primes we've found so far.
         (reduce (fn [primes i]
                   (if (== n (count primes))
                     (reduced primes)
                     (cond-> primes
                             (prime? primes i)
                             (conj i))))
                 ;; Adds the first and only even number.
                 [2]))))
