package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class MaxArryDequeTest {
    @Test
    public void testcapacity() {
        MaxArrayDeque<Water> waters=new MaxArrayDeque<>(new Water.capacitycompare());
        waters.addLast(new Water(550,"unkown",3));
        waters.addLast(new Water(500,"unkown",2));
        waters.addLast(new Water(1050,"unkown",4));
        waters.addLast(new Water(750,"unkown",4));
        waters.addLast(new Water(250,"unkown",1));
        waters.addLast(new Water(300,"unkown",4));
        Water expected = new Water(2000,"unkown",3);
        waters.addLast(expected);
        assertEquals(expected.getCapacity(),waters.max().getCapacity());
    }
    @Test
    public void testRandomOperations() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(new MyComparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        // Sequence of operations
        System.out.println("MaxArrayDeque.size() ==> " + deque.size());
        assertEquals(0, deque.size());

        deque.addFirst(1);
        System.out.println("MaxArrayDeque.addFirst(1)");

        deque.addFirst(2);
        System.out.println("MaxArrayDeque.addFirst(2)");

        deque.addLast(3);
        System.out.println("MaxArrayDeque.addLast(3)");
    }
}
