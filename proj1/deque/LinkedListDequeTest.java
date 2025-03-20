package deque;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }


    }
//    @Test
//    public void btest_node_add_empty_size() {
//        LinkedListDeque <Integer> deque = new LinkedListDeque();
//        for (int i = 5; i < 10; i++) {
//            deque.addLast(i);
//        }
//        for (int i = 4; i > 0; i--) {
//            deque.addFirst(i);
//        }
//        String expects="[1 2 3 4 5 6 7 8 9 ]";
//        assertEquals(9, deque.size());
//        assertEquals(expects, deque.toString());
//    }
    @Test
    public void test_get_1st() {
        LinkedListDeque <Integer> deque = new LinkedListDeque();
        for (int i = 5; i < 10; i++) {
            deque.addLast(i);
        }
        for (int i = 4; i > 0; i--) {
            deque.addFirst(i);
        }
        String expects="7";
        assertEquals(expects, deque.get(6).toString());
    }
    @Test
    public void test_get_2nd() {
        LinkedListDeque <Integer> deque = new LinkedListDeque();
        for (int i = 5; i < 10; i++) {
            deque.addLast(i);
        }
        for (int i = 4; i > 0; i--) {
            deque.addFirst(i);
        }
        String expects="1";
        assertEquals(expects, deque.get(0).toString());
        assertEquals(expects, deque.getRecursive(0).toString());
    }
    @Test
    public void test_get_3rd() {
        LinkedListDeque <Integer> deque = new LinkedListDeque();
        for (int i = 5; i < 10; i++) {
            deque.addLast(i);
        }
        for (int i = 4; i > 0; i--) {
            deque.addFirst(i);
        }
        String expects="9";
        String expectss="9";
        System.out.println(deque);
        assertEquals(expects, deque.get(8).toString());
        assertEquals(expectss, deque.getRecursive(8).toString());
    }
    @Test
    public void emptyLinkedListTest() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        assertTrue(deque.isEmpty());
        assertNull(deque.removeFirst());
        assertNull(deque.removeLast());
    }

    @Test
    public void testGetOutOfBounds() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        assertNull(deque.get(1));
        assertNull(deque.getRecursive(1));
    }

    @Test
    public void testEquals() {
        LinkedListDeque<Integer> deque1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> deque2 = new LinkedListDeque<>();
        assertTrue(deque1.equals(deque2));

        deque1.addFirst(1);
        deque2.addFirst(1);
        assertTrue(deque1.equals(deque2));

        deque1.addLast(2);
        deque2.addLast(2);
        assertTrue(deque1.equals(deque2));

        deque1.addLast(3);
        assertFalse(deque1.equals(deque2));
    }

    @Test
    public void testIterator() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        for (int i = 0; i < 5; i++) {
            deque.addLast(i);
        }
        int index = 0;
        for (int item : deque) {
            assertEquals((int) item, index);
            index++;
        }
    }

    @Test
    public void testAddRemoveAtBoundary() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals((Integer) i, deque.removeFirst());
        }
        assertTrue(deque.isEmpty());
    }
    @Test
    public void randomOperationsTest() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        Random rand = new Random();

        int operations = 1000;
        int maxValue = 100;
        int size = 0;

        for (int i = 0; i < operations; i++) {
            int operation = rand.nextInt(4);
            int value = rand.nextInt(maxValue);

            switch (operation) {
                case 0:
                    deque.addFirst(value);
                    size++;
                    break;
                case 1:
                    deque.addLast(value);
                    size++;
                    break;
                case 2:
                    if (size > 0) {
                        deque.removeFirst();
                        size--;
                    }
                    break;
                case 3:
                    if (size > 0) {
                        deque.removeLast();
                        size--;
                    }
                    break;
            }

            assertEquals(size, deque.size());

            if (size == 0) {
                assertTrue(deque.isEmpty());
            } else {
                assertFalse(deque.isEmpty());
            }
        }
    }
}
