package deque;

public interface Deque<witem> {
    public void addFirst(witem item);
    public void addLast(witem item);
    default public boolean isEmpty(){
        return size()==0;
    }
    public int size();
    public void printDeque();
    public witem removeFirst();
    public witem removeLast();
    public witem get(int index);
}
