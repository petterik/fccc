(ns fccc.core
  (:require [clojure.pprint :as pprint])
  (:gen-class))

(defn divisors
  "Returns a transducer filtering numbers in increasing order
   that divide i."
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
                        ;; Note: Using eduction instead of sequence as
                        ;;       eduction is twice as fast for this case.
                        (empty? (eduction (divisors i) primes))
                        (conj i))))
            ;; Adds the first and only even number.
            [2]
            ;; Generates all odd numbers.
            ;; Using range over iterate, as ranges implement IReduceInit.
            ;; Tradeoff: Limit's the algorithm, but makes it execute
            ;;           faster. I'm willing to limit the algorithm's max
            ;;           value as the algorithm is too slow to get to the
            ;;           limit anyway.
            (range 3 Long/MAX_VALUE 2))))

(defn prime-matrix
  "Generates a matrix where the first row and first column
   are prime numbers, and the rest of the numbers are products
   of to the corresponding header and column values."
  [n]
  (let [primes (n-primes n)]
    (->> primes
         (map (fn [prime]
                (map #(* prime %) (cons 1 primes))))
         (cons primes))))

(defn print-table
  "Prints a matrix with a header as a table with clojure.pprint."
  [matrix]
  (let [header (cons nil (first matrix))]
    (pprint/print-table
      (into []
            (map (fn [row] (into (sorted-map) (map vector header row))))
            (rest matrix)))))

(defn -main
  "Prints out a multiplication table of the first 10 prime number."
  [& args]
  (print-table (prime-matrix 10)))
