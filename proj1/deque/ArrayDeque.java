package deque;

import java.util.Iterator;

public class ArrayDeque<witem> implements Iterable<witem> {
    private witem[] arry;
    private int size;
    public ArrayDeque(witem d) {
        arry = (witem[]) new Object[8];
        arry[0] = d;
        size = 1;
    }
    public ArrayDeque() {
        arry = (witem[]) new Object[8];
        size = 0;
    }
    public class ArrayDequeIterator implements Iterator<witem> {
        private int pos = 0;
        public ArrayDequeIterator() {
            pos=0;
        }
        @Override
        public boolean hasNext() {
            return pos < size;
        }
        @Override
        public witem next() {
            return arry[pos++];
        }
    }
    @Override
    public Iterator<witem> iterator() {
        return new ArrayDequeIterator();
    }

    //base do
    public void resize(int newSize,boolean first,boolean delate) {
        witem[] newarry = (witem[]) new Object[newSize];
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
    public void addFirst(witem d) {
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
    public void addLast(witem d) {
        if(size >= arry.length) {
            resize(arry.length*2,false,false);
        }
        arry[size] = d;
        size++;
        return ;
    }
    public witem removeFirst() {
        if(size == 0) {
            return null;
        }
        witem lost = arry[0];
        size--;
        if(if_resize()){
            resize(arry.length/2,true,true);
        }
        else {
            resize(arry.length,true,true);
        }
        return lost;
    }
    public witem removeLast() {
        if(size == 0) {
            return null;
        }
        witem lost = arry[size-1];
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

    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }

    //about output


    @Override
    public String toString() {
        StringBuilder base=new StringBuilder();
        base.append("[");
        for(witem i:this)
        {
            base.append(i.toString());
            base.append(" ");
        }
        base.append("]");
        return base.toString();
    }
    public witem get(int index) {
        if(index < 0 || index >= size){
            return null;
        }
        return arry[index];
    }
    public void printDeque() {
        System.out.println(arry);
        return ;
    }

    //

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
