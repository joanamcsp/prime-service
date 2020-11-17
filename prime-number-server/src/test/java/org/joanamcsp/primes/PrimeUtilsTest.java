package org.joanamcsp.primes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PrimeUtilsTest {

    @Test
    public void isPrime_returns_true_if_number_is_prime() {
        assertTrue(PrimeUtils.isPrime(2));
        assertTrue(PrimeUtils.isPrime(5));
        assertTrue(PrimeUtils.isPrime(7));
        assertTrue(PrimeUtils.isPrime(11));
    }

    @Test
    public void isPrime_returns_false_if_number_is_less_than_2() {
        assertFalse(PrimeUtils.isPrime(-1));
        assertFalse(PrimeUtils.isPrime(-0));
        assertFalse(PrimeUtils.isPrime(1));
    }

    @Test
    public void isPrime_returns_false_if_number_greater_than_2_is_not_prime() {
        assertFalse(PrimeUtils.isPrime(4));
        assertFalse(PrimeUtils.isPrime(6));
        assertFalse(PrimeUtils.isPrime(9));
        assertFalse(PrimeUtils.isPrime(15));
    }
}
