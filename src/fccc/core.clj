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
  "Returns n prime numbers.

  Psuedo code:
  primes = [2];
  For each positive odd number i:
    For each prime p found so far:
      If i is divisible by p:
        Add i to the collection of primes.

  This code runs in O(P*Q), where:
    P are the set of primes.
    Q are the set of odd numbers + 1.
    N are the set of all positive numbers.
    Using Cantor's theories around infinite numbers, we learn
    that P, Q and N are all the same, in what's called ℵ0.
    Thus the code runs in O(N^2)."
  [n]
  (when (pos? n)
    ;; Using reduce to have the primes we've found so far.
    (reduce (fn [primes i]
              (if (== n (count primes))
                (reduced primes)
                (cond-> primes
                        ;; If there are no divisors to i, it's a prime.
                        (empty? (eduction (divisors i) primes))
                        (conj i))))
            ;; Adds the first and only even number.
            [2]
            ;; Generates all odd numbers.
            ;; Using range over iterate, as ranges implement IReduceInit.
            (range 3 Long/MAX_VALUE 2))))

(defn prime-matrix [n]
  (let [primes (n-primes n)]
    (->> (rest primes)
         (map (fn [prime]
                (map #(* prime %) (cons 1 (rest primes)))))
         (cons primes))))

