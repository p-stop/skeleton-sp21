package deque;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized ArrayDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());
    }

    @Test
    public void removeEmptyTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    public void multipleParamTest() {
        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    public void emptyNullReturnTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());
    }

    @Test
    public void bigArrayDequeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 50; i++) {
            assertEquals("Should have the same value", i,  ad1.removeFirst(), 0.0);
        }

        for (double i = 99; i > 50; i--) {
            assertEquals("Should have the same value", i,  ad1.removeLast(), 0.0);
        }
    }

//    @Test
//    public void btest_node_add_empty_size() {
//        ArrayDeque <Integer> deque = new ArrayDeque();
//        for (int i = 5; i < 10; i++) {
//            deque.addLast(i);
//        }
//        for (int i = 4; i > 0; i--) {
//            deque.addFirst(i);
//        }
//        String expects="[1 2 3 4 5 6 7 8 9 ]";
//        assertEquals(9, deque.size());
//        assertEquals(expects, deque.printDeque());
//    }

    @Test
    public void test_get_1st() {
        ArrayDeque <Integer> deque = new ArrayDeque();
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
        ArrayDeque <Integer> deque = new ArrayDeque();
        for (int i = 5; i < 10; i++) {
            deque.addLast(i);
        }
        for (int i = 4; i > 0; i--) {
            deque.addFirst(i);
        }
        String expects="1";
        assertEquals(expects, deque.get(0).toString());
    }

    @Test
    public void test_get_3rd() {
        ArrayDeque <Integer> deque = new ArrayDeque();
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
    }

    @Test
    public void emptyArrayDequeTest() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertTrue(deque.isEmpty());
        assertNull(deque.removeFirst());
        assertNull(deque.removeLast());
    }

    @Test
    public void testGetOutOfBounds() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        assertNull(deque.get(1));
    }

    @Test
    public void testEquals() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
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
        ArrayDeque<Integer> deque = new ArrayDeque<>();
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
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals((Integer) i, deque.removeFirst());
        }
        assertTrue(deque.isEmpty());
    }
}