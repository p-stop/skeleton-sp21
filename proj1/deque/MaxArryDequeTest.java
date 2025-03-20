package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class MaxArryDequeTest {
    @Test
    public void testcapacity() {
        MaxArrayDeque<Water> waters=new MaxArrayDeque<>(Water.capacitycompare);
        waters.addLast(new Water(550,"unkown",3));
        waters.addLast(new Water(500,"unkown",2));
        waters.addLast(new Water(1050,"unkown",4));
        waters.addLast(new Water(750,"unkown",4));
        waters.addLast(new Water(250,"unkown",1));
        waters.addLast(new Water(300,"unkown",4));
        Water expected = new Water(2000,"unkown",3);
        waters.addLast(expected);
        assertEquals(expected,waters.max());
    }
}
