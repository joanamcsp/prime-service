package org.joanamcsp.primes;

import static org.junit.Assert.assertEquals;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class PrimesTest {

    private static int MAX_BOUND = 100_000;

    @Test
    public void getPrimes_given_bound_returns_stream_of_primes_up_to_bound() {
        assertEquals(List.of(2,3,5,7), Primes.getPrimes(10).boxed().collect(Collectors.toList()));
    }

    @Test
    public void getPrimes_given_bound_is_2_returns_stream_with_one_element() {
        assertEquals(List.of(2), Primes.getPrimes(2).boxed().collect(Collectors.toList()));
    }

    @Test
    public void getPrimes_give_bound_is_less_than_2_returns_empty_stream() {
        assertEquals(Collections.emptyList(), Primes.getPrimes(-1).boxed().collect(Collectors.toList()));
    }

    @Test
    public void getPrimes_give_bound_is_greater_than_max_bound_returns_stream_of_values_up_to_max_bound() {
        assertEquals( Primes.getPrimes(MAX_BOUND).boxed().collect(Collectors.toList()), Primes.getPrimes(300_000).boxed().collect(Collectors.toList()));
    }
}
