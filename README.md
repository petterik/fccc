# fccc - Funding Circle Code Challenge

## Objective
"Write a program that prints out a multiplication table of the first 10 prime number."

## Usage

Code has been packaged and checked in to `git`. Runnable with:
* `run.sh`
* `java -jar target/fccc-0.1.0-SNAPSHOT-standalone.jar`
* `lein run`

If you want to build it yourself, run `lein uberjar`.

### Output

```
|    |  2 |  3 |   5 |   7 |  11 |  13 |  17 |  19 |  23 |  29 |
|----+----+----+-----+-----+-----+-----+-----+-----+-----+-----|
|  2 |  4 |  6 |  10 |  14 |  22 |  26 |  34 |  38 |  46 |  58 |
|  3 |  6 |  9 |  15 |  21 |  33 |  39 |  51 |  57 |  69 |  87 |
|  5 | 10 | 15 |  25 |  35 |  55 |  65 |  85 |  95 | 115 | 145 |
|  7 | 14 | 21 |  35 |  49 |  77 |  91 | 119 | 133 | 161 | 203 |
| 11 | 22 | 33 |  55 |  77 | 121 | 143 | 187 | 209 | 253 | 319 |
| 13 | 26 | 39 |  65 |  91 | 143 | 169 | 221 | 247 | 299 | 377 |
| 17 | 34 | 51 |  85 | 119 | 187 | 221 | 289 | 323 | 391 | 493 |
| 19 | 38 | 57 |  95 | 133 | 209 | 247 | 323 | 361 | 437 | 551 |
| 23 | 46 | 69 | 115 | 161 | 253 | 299 | 391 | 437 | 529 | 667 |
| 29 | 58 | 87 | 145 | 203 | 319 | 377 | 493 | 551 | 667 | 841 |
```

## Thought process

The objective is to print a multiplication table of the first 10 primes. To do this, we need prime numbers and the challenge states that I should write my own code to do this. Let's start with that.

### Prime number generator
I've decided to write my own prime generating algorithm, without looking at the state of the art, to show that I can come up with one, analyze it and make it run as fast as I can. I start write test cases for the prime generating function and not a "prime number checking" as it's an implementation detail that can limit the possible implementations of the higher level function. Here are some of the test cases I considered:
* Trying to get 0 or -1 prime numbers => Returns empty seq
* My function should generate the first known primes, starting with 2,3.
* There shouldn't be any even numbers after the first one.
* The 100th prime should be 541 and the 1000th prime 7919.

All the versions of my prime generating function are in the git commit history. I'll include some of them here. I started by implementing a naive prime generator:
```clj
(defn n-primes [n]
  (when (pos? n)
    (->> (iterate (comp inc inc) 3)
         (filter (fn [new]
                   (every? #(ratio? (/ new %)) (range 3 new))))
         (cons 2)
         (take n))))
```
This function will try to divide against any number, instead of the primes we've already found. To change this I used `reduce` instead of `filter`:
```clj
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
```
This function will only try to divide against the found primes and will stop when we've found `n` `primes`. An optimized version of this one with comments is what's in the final version.

The analysis of the algorithm is in the doc string, as well as copied here:
```clj
user.repl=> (clojure.repl/doc fccc.core/n-primes)
-------------------------
fccc.core/n-primes
([n])
  Returns n prime numbers.

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
    Thus the code runs in O(N^2).
```


Now that we can generate primes, let's create the multiplication table, then worry about how to print it.

### Multiplication table (matrix)

For the multiplication table, we want to create a matrix with the sums of the first prime to the last prime. This would be a multiplication table of the first 3 primes:
```clj
[[   2  3  5]
 [2  4  6 10]
 [3  6  9 15]
 [5 10 15 25]]
```
Test cases for this were:
* First row and first column should be equals to n primes, where n is the size of the matrix's first row.
* Second row's second value is 2x2=4.
* All rows but the first one is of size n+1.
* Last row's last value is the (nth prime)^2

### Printing the table

There's a table printing function in the `clojure.pprint` namespace, which takes which keys to print and a seq of maps. The matrix with 3 primes printed above gets printed like this:
```
|   |  2 |  3 |  5 |
|---+----+----+----|
| 2 |  4 |  6 | 10 |
| 3 |  6 |  9 | 15 |
| 5 | 10 | 15 | 25 |
```

The implementation of printing this is not tested, but it was clear that it worked when it was executed. Here's the implementation.
```clj
(defn print-table
  "Prints a matrix with a header as a table with clojure.pprint."
  [matrix]
  (let [header (cons nil (first matrix))]
    (pprint/print-table
      (into []
            (map (fn [row] (into (sorted-map) (map vector header row))))
            (rest matrix)))))
```

## License

Copyright © 2017 Petter Eriksson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
