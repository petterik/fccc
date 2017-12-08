(ns fccc.core)

(defn prime? [primes ^long i]
  (let [largest-factor (long (inc (Math/sqrt i)))]
    (->> primes
         (eduction
           ;; Only takes until the largest possible factor.
           (take-while (fn [^long prime]
                         (<= prime largest-factor)))
           ;; Using type hints and `rem` instead of `/` for perf.
           (filter (fn [^long prime]
                     (== 0 (rem i prime)))))
         ;; Prime if we could not divide the number i.
         (empty?))))

(defn n-primes [n]
  (when (pos? n)
    ;; Using reduce to have the primes we've found so far.
    (reduce (fn [primes i]
              (if (== n (count primes))
                (reduced primes)
                (cond-> primes
                        (prime? primes i)
                        (conj i))))
            ;; Adds the first and only even number.
            [2]
            ;; Generates all odd numbers.
            ;; Using range over iterate, as ranges implement IReduceInit.
            (range 3 Long/MAX_VALUE 2))))

