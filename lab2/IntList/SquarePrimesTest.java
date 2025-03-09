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
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimesSimple1() {
        IntList lst = IntList.of(0,1,2,3,4,1,1,17,1);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("0 -> 1 -> 4 -> 9 -> 4 -> 1 -> 1 -> 289 -> 1", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimesSimple2() {
        IntList lst = IntList.of(6,8,10,9,4);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("6 -> 8 -> 10 -> 9 -> 4", lst.toString());
        assertFalse(changed);
    }
    @Test
    public void tt33() {
        IntList lst = IntList.of(2,3,5,7);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 9 -> 25 -> 49", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testempty() {
        IntList lst = IntList.of(2,2,2,2,2,2,2);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 4 -> 4 -> 4 -> 4 -> 4 -> 4", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void t5() {
        IntList lst = IntList.of(1,1,1,1,1,1,1);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 1 -> 1 -> 1 -> 1 -> 1 -> 1", lst.toString());
        assertFalse(changed);
    }
    @Test
    public void testEmptyList() {
        IntList list = null;
        boolean result = IntListExercises.squarePrimes(list);
        assertFalse(result);
    }

    // 测试列表中没有素数的情况
    @Test
    public void testNoPrimesInList() {
        IntList list = new IntList(4, new IntList(6, null));
        boolean result = IntListExercises.squarePrimes(list);
        assertFalse(result);
        assertEquals(4, list.first);
        assertEquals(6, list.rest.first);
    }

    // 测试列表中只有一个素数的情况
    @Test
    public void testSinglePrimeInList() {
        IntList list = new IntList(2, null);
        boolean result = IntListExercises.squarePrimes(list);
        assertTrue(result);
        assertEquals(4, list.first);
    }

    // 测试列表中多个元素，第一个元素是素数，后续无素数
    @Test
    public void testFirstElementIsPrime() {
        IntList list = new IntList(3, new IntList(4, null));
        boolean result = IntListExercises.squarePrimes(list);
        assertTrue(result);
        assertEquals(9, list.first);
        assertEquals(4, list.rest.first);
    }

    // 测试列表中多个元素，第一个元素不是素数，后续有素数
    @Test
    public void testLaterElementIsPrime() {
        IntList list = new IntList(4, new IntList(5, null));
        boolean result = IntListExercises.squarePrimes(list);
        assertTrue(result);
        assertEquals(4, list.first);
        assertEquals(25, list.rest.first);
    }

    // 测试列表中多个元素都是素数的情况
    @Test
    public void testAllPrimesInList() {
        IntList list = new IntList(2, new IntList(3, new IntList(5, null)));
        boolean result = IntListExercises.squarePrimes(list);
        assertTrue(result);
        assertEquals(4, list.first);
        assertEquals(9, list.rest.first);
        assertEquals(25, list.rest.rest.first);
    }

    // 测试列表中素数和非素数交替出现的情况
    @Test
    public void testMixedPrimesAndNonPrimes() {
        IntList list = new IntList(2, new IntList(4, new IntList(3, null)));
        boolean result = IntListExercises.squarePrimes(list);
        assertTrue(result);
        assertEquals(4, list.first);
        assertEquals(4, list.rest.first);
        assertEquals(9, list.rest.rest.first);
    }
}

