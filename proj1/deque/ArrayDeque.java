package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {

    private T[] arry;

    private int size;

    //construct

    public ArrayDeque(T d) {
        arry = (T[]) new Object[8];
        arry[0] = d;
        size = 1;
    }

    public ArrayDeque() {
        arry = (T[]) new Object[8];
        size = 0;
    }

    //iterator

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos = 0;
        public ArrayDequeIterator() {
            pos=0;
        }
        @Override
        public boolean hasNext() {
            return pos < size;
        }
        @Override
        public T next() {
            return arry[pos++];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    //base do

    public void resize(int newSize,boolean first,boolean delate) {
        T[] newarry = (T[]) new Object[newSize];
        int pos = 0;
        int start=0;
        if(delate) {
            if(first) {
                start = 1;
            }
        }
        else{
            if(first) {
                pos=1;
            }
        }
        System.arraycopy(arry, start, newarry, pos, size);
        arry = newarry;
        return ;
    }

    @Override
    public void addFirst(T d) {
        if(size >= arry.length) {
            resize(arry.length*2,true,false);
        }
        else
        {
            resize(arry.length,true,false);
        }
        arry[0] = d;
        size++;
        return ;
    }

    @Override
    public void addLast(T d) {
        if(size >= arry.length) {
            resize(arry.length*2,false,false);
        }
        arry[size] = d;
        size++;
        return ;
    }

    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        T lost = arry[0];
        size--;
        if(if_resize()){
            resize(arry.length/2,true,true);
        }
        else {
            resize(arry.length,true,true);
        }
        return lost;
    }

    public T removeLast() {
        if(size == 0) {
            return null;
        }
        T lost = arry[size-1];
        size--;
        if(if_resize()){
            resize(arry.length/2,false,true);
        }
        else {
            resize(arry.length,false,true);
        }
        return lost;
    }

    //about size

    @Override
    public int size() {
        return size;
    }

    //about output


    @Override
    public String toString() {
        StringBuilder base=new StringBuilder();
        base.append("[");
        for(T i:this)
        {
            base.append(i.toString());
            base.append(" ");
        }
        base.append("]");
        return base.toString();
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size){
            return null;
        }
        return arry[index];
    }

    @Override
    public void printDeque() {
        System.out.println(arry);
        return ;
    }

    //compare

    public boolean equals(Object o) {
        if(o instanceof ArrayDeque) {
            ArrayDeque otherarry = (ArrayDeque) o;
            if(otherarry.size != size){
                return false;
            }
            for(int i=0;i<size;i++){
                if(!arry[i].equals(otherarry.get(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    //helper
    private boolean if_resize() {
        if(arry.length > size*4){
            return true;
        }
        return false;
    }
}
