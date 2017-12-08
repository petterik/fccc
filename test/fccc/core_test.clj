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
  (criterium/bench (fccc/n-primes 1e4)
                   :target-execution-time 10))


