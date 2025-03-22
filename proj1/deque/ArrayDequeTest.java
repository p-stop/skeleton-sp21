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
    @Test
    public void testOperationsSequence() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // Test isEmpty() when deque is empty
        assertTrue(deque.isEmpty());

        // Test addFirst() and removeFirst()
        deque.addFirst(1);
        assertEquals(Integer.valueOf(1), deque.removeFirst());

        deque.addFirst(3);
        assertEquals(Integer.valueOf(3), deque.removeFirst());

        // Test isEmpty() again
        assertTrue(deque.isEmpty());
        assertTrue(deque.isEmpty());

        deque.addFirst(7);
        assertEquals(Integer.valueOf(7), deque.removeFirst());

        deque.addFirst(9);
        assertEquals(Integer.valueOf(9), deque.removeFirst());

        deque.addFirst(11);
        // No removeFirst() for 11, as it's not specified in the sequence
    }
    @Test
    public void testRandomOperations() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            double operation = rand.nextDouble();

            if (operation < 0.8) {
                // 80% chance to addLast
                int value = rand.nextInt(100);
                deque.addLast(value);
                System.out.println("addLast(" + value + ")");
            } else if (operation < 0.9) {
                // 10% chance to removeFirst
                if (!deque.isEmpty()) {
                    int removed = deque.removeFirst();
                    System.out.println("removeFirst() ==> " + removed);
                }
            } else {
                // 10% chance to check isEmpty
                boolean isEmpty = deque.isEmpty();
                System.out.println("isEmpty() ==> " + isEmpty);
            }
        }
    }
    @Test
    public void testRandomOperations_pro() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Random rand = new Random();

        // Define probabilities for each operation
        double[] probabilities = new double[]{0.3, 0.3, 0.1, 0.1, 0.1, 0.1};

        // Perform random operations
        for (int i = 0; i < 1000; i++) {
            double operation = rand.nextDouble();

            if (operation < probabilities[0]) {
                // 30% chance to addFirst
                int value = rand.nextInt(100);
                deque.addFirst(value);
                System.out.println("addFirst(" + value + ")");
            } else if (operation < probabilities[0] + probabilities[1]) {
                // 30% chance to addLast
                int value = rand.nextInt(100);
                deque.addLast(value);
                System.out.println("addLast(" + value + ")");
            } else if (operation < probabilities[0] + probabilities[1] + probabilities[2]) {
                // 10% chance to removeFirst
                if (!deque.isEmpty()) {
                    int removed = deque.removeFirst();
                    System.out.println("removeFirst() ==> " + removed);
                }
            } else if (operation < probabilities[0] + probabilities[1] + probabilities[2] + probabilities[3]) {
                // 10% chance to removeLast
                if (!deque.isEmpty()) {
                    int removed = deque.removeLast();
                    System.out.println("removeLast() ==> " + removed);
                }
            } else if (operation < probabilities[0] + probabilities[1] + probabilities[2] + probabilities[3] + probabilities[4]) {
                // 10% chance to check isEmpty
                boolean isEmpty = deque.isEmpty();
                System.out.println("isEmpty() ==> " + isEmpty);
            } else {
                // 10% chance to check size
                int size = deque.size();
                System.out.println("size() ==> " + size);
            }
        }
    }
        @Test
        public void testArrayDequeEquals() {
            ArrayDeque<Integer> ad1 = new ArrayDeque<>();
            ArrayDeque<Integer> ad2 = new ArrayDeque<>();

            // Add elements to both deques
            ad1.addLast(1);
            ad1.addLast(2);
            ad1.addLast(3);

            ad2.addLast(1);
            ad2.addLast(2);
            ad2.addLast(3);

            // Check equality
            assertTrue(ad1.equals(ad2));
            assertTrue(ad2.equals(ad1));
        }

        @Test
        public void testLinkedListDequeEquals() {
            LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
            LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();

            // Add elements to both deques
            lld1.addLast(1);
            lld1.addLast(2);
            lld1.addLast(3);

            lld2.addLast(1);
            lld2.addLast(2);
            lld2.addLast(3);

            // Check equality
            assertTrue(lld1.equals(lld2));
            assertTrue(lld2.equals(lld1));
        }

        @Test
        public void testArrayDequeAndLinkedListDequeEquals() {
            ArrayDeque<Integer> ad = new ArrayDeque<>();
            LinkedListDeque<Integer> lld = new LinkedListDeque<>();

            // Add elements to both deques
            ad.addLast(1);
            ad.addLast(2);
            ad.addLast(3);

            lld.addLast(1);
            lld.addLast(2);
            lld.addLast(3);

            // Check equality
            assertTrue(ad.equals(lld));
            assertTrue(lld.equals(ad));
        }

        @Test
        public void testArrayDequeNotEquals() {
            ArrayDeque<Integer> ad1 = new ArrayDeque<>();
            ArrayDeque<Integer> ad2 = new ArrayDeque<>();

            // Add elements to both deques
            ad1.addLast(1);
            ad1.addLast(2);
            ad1.addLast(3);

            ad2.addLast(1);
            ad2.addLast(2);
            ad2.addLast(4); // Different element

            // Check equality
            assertFalse(ad1.equals(ad2));
            assertFalse(ad2.equals(ad1));
        }

        @Test
        public void testLinkedListDequeNotEquals() {
            LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
            LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();

            // Add elements to both deques
            lld1.addLast(1);
            lld1.addLast(2);
            lld1.addLast(3);

            lld2.addLast(1);
            lld2.addLast(2);
            lld2.addLast(4); // Different element

            // Check equality
            assertFalse(lld1.equals(lld2));
            assertFalse(lld2.equals(lld1));
        }

        @Test
        public void testArrayDequeAndLinkedListDequeNotEquals() {
            ArrayDeque<Integer> ad = new ArrayDeque<>();
            LinkedListDeque<Integer> lld = new LinkedListDeque<>();

            // Add elements to both deques
            ad.addLast(1);
            ad.addLast(2);
            ad.addLast(3);

            lld.addLast(1);
            lld.addLast(2);
            lld.addLast(4); // Different element

            // Check equality
            assertFalse(ad.equals(lld));
            assertFalse(lld.equals(ad));
        }

        @Test
        public void testArrayDequeEqualsNull() {
            ArrayDeque<Integer> ad = new ArrayDeque<>();

            // Check equality with null
            assertFalse(ad.equals(null));
        }

        @Test
        public void testLinkedListDequeEqualsNull() {
            LinkedListDeque<Integer> lld = new LinkedListDeque<>();

            // Check equality with null
            assertFalse(lld.equals(null));
        }

        @Test
        public void testArrayDequeAndLinkedListDequeEqualsNull() {
            ArrayDeque<Integer> ad = new ArrayDeque<>();
            LinkedListDeque<Integer> lld = new LinkedListDeque<>();

            // Check equality with null
            assertFalse(ad.equals(null));
            assertFalse(lld.equals(null));
        }

}