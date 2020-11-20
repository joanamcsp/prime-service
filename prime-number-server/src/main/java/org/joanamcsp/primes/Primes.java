package org.joanamcsp.primes;

import java.util.BitSet;
import java.util.stream.IntStream;

public class Primes {

    final private static int MAX_BOUND = 100_000;
    final private static BitSet primes = initPrimes(MAX_BOUND);

    public static IntStream getPrimes(int bound) {
        return IntStream.rangeClosed(2, bound)
                .filter(i -> primes.get(i));
    }

    private static BitSet initPrimes(int bound) {
        int max = MAX_BOUND;
        if (bound >= 2) {
            max = bound;
        }
        BitSet primeSet = new BitSet(max);
        primeSet.set(2, max);
        int currentPrime = 2;

        while (currentPrime <= Math.sqrt(max)) {
            markAsNotPrime(primeSet, currentPrime, max);
            currentPrime = getNextPrime(primeSet, currentPrime, max);
        }
        return primeSet;
    }

    private static void markAsNotPrime(BitSet primes, int prime, int max) {
        for (long i = prime * prime; i < max ; i += prime) {
            primes.clear((int)i);
        }
    }

    private static int getNextPrime(BitSet primes, int prime, int max) {
        int next = prime + 1;
        while(next < max - 1 && !primes.get(next)) {
            next++;
        }
        return next;
    }
}
