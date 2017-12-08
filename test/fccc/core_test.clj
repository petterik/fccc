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

(deftest prime-matrix-test
  (let [primes (fccc/n-primes 10)
        matrix (fccc/prime-matrix 10)]

    (testing "First row should be equal to the primes"
      (is (= (first matrix) primes)))
    (testing "First column should be equal to primes"
      (is (= (map first matrix) primes)))

    (testing "Second row's last item should be last prime * second prime"
      (is (= (* (second primes) (last primes))
             (-> matrix second last))))

    (testing "Second row, second item should be 3x3=9"
      (is (= 9 (-> matrix second second))))

    (testing "All rows should be the same length"
      (is (every? #{(count primes)} (map count matrix))))

    (testing "last row and last item should equal last prime * last prime"
      (is (= (* (last primes) (last primes))
             (-> matrix last last))))))


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
