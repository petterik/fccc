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

Given how the challenge was described, I decided to write my own prime generating algorithm without looking at the state of the art. I've written tests (first) for the algorithm and benchmarked it. Tests and benchmarks are found in `test/fcc/core_test.clj`.

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

## License

Copyright © 2017 Petter Eriksson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
