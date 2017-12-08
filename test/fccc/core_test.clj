(ns fccc.core-test
  (:require [clojure.test :refer :all]
            [fccc.core :as fccc]
            [criterium.core :as criterium]))

(deftest n-primes
  (let [first-primes [2 3 5 7 11 13 17 19 23]]
    (is (empty? (fccc/n-primes 0)))
    (is (= first-primes
           (fccc/n-primes (count first-primes))))
    (is (not-any? even? (rest (fccc/n-primes 100))))
    (is (= 541 (last (fccc/n-primes 100))))
    (is (= 7919 (last (fccc/n-primes 1000))))))



(comment
  ;; To run benchmarks
  (criterium/bench (fccc/n-primes 1e4))

  ;; Output on my MacBook Pro 13" (early 2015)
  ;; Evaluation count : 1260 in 60 samples of 21 calls.
  ;; Execution time mean : 47.045036 ms
  ;; Execution time std-deviation : 1.029962 ms
  ;; Execution time lower quantile : 45.188445 ms ( 2.5%)
  ;; Execution time upper quantile : 48.331213 ms (97.5%)
  ;; Overhead used : 1.861958 ns
  )
