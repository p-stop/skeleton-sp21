package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    // 测试列表中没有素数的情况
    @Test
    public void testNoPrimesInList() {
        IntList lst = IntList.of(4, 6, 8, 9);
        boolean changed = squarePrimes(lst);
        assertEquals("4 -> 6 -> 8 -> 9", lst.toString());
        assertFalse(changed);
    }

    // 测试列表中只有一个素数的情况
    @Test
    public void testSinglePrimeInList() {
        IntList lst = IntList.of(2);
        boolean changed = squarePrimes(lst);
        assertEquals("4", lst.toString());
        assertTrue(changed);
    }

    // 测试列表中多个素数的情况
    @Test
    public void testMultiplePrimesInList() {
        IntList lst = IntList.of(2, 3, 5);
        boolean changed = squarePrimes(lst);
        assertEquals("4 -> 9 -> 25", lst.toString());
        assertTrue(changed);
    }

    // 测试列表中素数和非素数交替出现的情况
    @Test
    public void testMixedPrimesAndNonPrimes() {
        IntList lst = IntList.of(4, 2, 6, 3);
        boolean changed = squarePrimes(lst);
        assertEquals("4 -> 4 -> 6 -> 9", lst.toString());
        assertTrue(changed);
    }

    public static boolean squarePrimes(IntList lst) {
        if (lst == null) {
            return false;
        }
        boolean currElemIsPrime = Primes.isPrime(lst.first);
        if (currElemIsPrime) {
            lst.first *= lst.first;
        }
        return squarePrimes(lst.rest) || currElemIsPrime;
    }
}

