package deque;

import java.util.Comparator;

public interface MyComparator<T> extends Comparator<T> {
     int  compare(T a, T b);
}
