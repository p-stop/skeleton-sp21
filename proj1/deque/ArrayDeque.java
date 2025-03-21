package deque;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] arry;

    //head上有元素，tail上没有
    private int tail;
    private int head;

    public ArrayDeque() {
        arry = (T[]) new Object[8];
        tail = 2;
        head = 2;
    }

    //iterator

    private class ArrayDequeIterator<T> implements Iterator<T> {
        private int pos = 0;
        public ArrayDequeIterator() {
            pos=head;
        }
        @Override
        public boolean hasNext() {
            return tail-pos>0;
        }
        @Override
        public T next() {
            return (T)arry[pos++];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private void resize(int newSize,int head) {
        if(newSize<=8){
            newSize=8;
        }
        T[] newarry = (T[]) new Object[newSize];
        int pos = newSize/4;
        int start=head;
        System.arraycopy(arry, start, newarry, pos, size());
        tail=pos+size();
        this.head=pos;
        arry = newarry;
        return ;
    }

    @Override
    public void addFirst(T d) {
        if(head >0) {
            arry[--head] = d;
        }
        else{
            resize(size()*2,head);
            arry[--head] = d;

        }
        return ;
    }

    @Override
    public void addLast(T d) {
        if(tail>=arry.length-1) {
            resize(size()*2,head);
        }
        arry[tail++] = d;
        return ;
    }

    public T removeFirst() {
        if(size() == 0) {
            return null;
        }
        T lost = arry[head];
        head++;
        if(if_resize()){
            resize(arry.length/2,head);
        }
        return lost;
    }

    public T removeLast() {
        if(size() == 0) {
            return null;
        }
        T lost = arry[tail-1];
        tail--;
        if(if_resize()){
            resize(arry.length/2,head);
        }
        return lost;
    }

    //about size

    @Override
    public int size() {
        return tail-head;
    }

    //about output


//    @Override
//    public String toString() {
//        StringBuilder base=new StringBuilder();
//        base.append("[");
//        for(T i:this)
//        {
//            base.append(i.toString());
//            base.append(" ");
//        }
//        base.append("]");
//        return base.toString();
//    }

    @Override
    public T get(int index) {
        if(index < 0 || index+1 > size()){
            return null;
        }
        return arry[head+index];
    }

    @Override
    public void printDeque() {
        for(int i=head;i<tail;i++){
            System.out.print(arry[i]);
        }
        return ;
    }
    public boolean equals(Object o) {
        if(o==this){
            return true;
        }
        if(o instanceof ArrayDeque) {
            ArrayDeque<T> other = (ArrayDeque<T>) o;
            if(size() != other.size()) {
                return false;
            }
            for(int i=other.head; i<size(); i++) {
                if(arry[i] != other.arry[i]) {
                    return false;
                }
            }
            return true;
        } else if (o instanceof LinkedListDeque) {
            LinkedListDeque<T> other = (LinkedListDeque<T>) o;
            if(size() != other.size()) {
                return false;
            }
            Iterator<T> it1 = other.iterator();
            Iterator<T> it2 = this.iterator();
            for(int i=0; i<size(); i++) {
                if(it2.next() != it1.next()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    //helper
    private boolean if_resize() {
        if(arry.length<=8){
            return false;
        }
        else if(arry.length > size()*4){
            return true;
        }
        return false;
    }
}
