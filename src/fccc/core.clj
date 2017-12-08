(ns fccc.core)

(defn divisors
  "Returns a transducer filtering the numbers that divide i."
  [^long i]
  (let [largest-factor (long (inc (Math/sqrt i)))]
    (comp
      ;; Only takes until the largest possible factor.
      (take-while (fn [^long num] (<= num largest-factor)))
      ;; Using type hints and `rem` instead of `/` for perf.
      (filter (fn [^long num] (== 0 (rem i num)))))))

(defn n-primes
  "Returns n prime numbers."
  [n]
  (when (pos? n)
    ;; Using reduce to have the primes we've found so far.
    (reduce (fn [primes i]
              (if (== n (count primes))
                (reduced primes)
                (cond-> primes
                        (empty? (eduction (divisors i) primes))
                        (conj i))))
            ;; Adds the first and only even number.
            [2]
            ;; Generates all odd numbers.
            ;; Using range over iterate, as ranges implement IReduceInit.
            (range 3 Long/MAX_VALUE 2))))

