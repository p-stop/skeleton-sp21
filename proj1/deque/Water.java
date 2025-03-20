package deque;

import java.util.Comparator;

public class Water implements MyComparable<Water>{

    private int capacity;
    private String origin;
    private int quality;
    @Override
    public int compareTo(Water item) {
        return this.quality-item.quality;
    }
    public static class capacitycompare implements MyComparator<Water>{
        public  int  compare(Water a, Water b) {
            return a.capacity-b.capacity;
        }
    }
    public Water(int capacity, String origin, int quality) {
        this.capacity = capacity;
        this.origin = origin;
        this.quality = quality;
    }
}
