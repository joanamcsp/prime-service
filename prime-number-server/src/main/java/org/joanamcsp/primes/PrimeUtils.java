package org.joanamcsp.primes;

public class PrimeUtils {
    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        int sqroot = (int) Math.sqrt(number);
        for (int i = 2; i <= sqroot; i++) {
            if (number % i == 0){
                return false;
            }
        }
        return true;
    }
}
