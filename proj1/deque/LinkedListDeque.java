package deque;

import java.util.Iterator;


public class LinkedListDeque<witem> implements Iterable<witem>,Deque<witem> {

    //node

    private class Node {
        witem data;
        Node next;
        Node prev;
        public Node(witem d, Node n, Node p) {
            data = d;
            next = n;
            prev = p;
        }
    }

    private Node heail;

    private int size;

    //construct

    public LinkedListDeque() {
        heail = new Node(null, null, null);
        heail.next = heail.prev = heail;
        size = 0;
    }

    public LinkedListDeque(witem d) {
        heail = new Node(null, null, null);
        Node newnode=new Node(d,heail,heail);
        heail.next = heail.prev = newnode;
        size = 1;
    }

    public LinkedListDeque(witem []arry) {
        heail = new Node(null,null,null);
        heail.next = heail.prev = heail;
        size = 0;
        for(witem d : arry) {
            this.addLast(d);
        }
    }

    //iterator implement

    public class Listiterator<witem> implements Iterator<witem> {

        private Node current;
        public Listiterator() {
            current = heail.next;
        }
        @Override
        public boolean hasNext() {
            return current != heail;
        }

        @Override
        public witem next() {
            witem cur_data= (witem) current.data;
            current=current.next;
            return cur_data;
        }
    }

    public Listiterator<witem> iterator(){
        return new Listiterator<witem>();
    }

    //base

    @Override
    public void addFirst(witem d) {
        Node newNode = new Node(d, heail.next, heail);
        heail.next = newNode;
        newNode.next.prev = newNode;
        size++;
        return;
    }

    public witem removeFirst(){
        if (size == 0) return null;
        witem data = heail.next.data;
        heail.next=heail.next.next;
        heail.next.prev=heail;
        size--;
        return data;
    }

    @Override
    public void addLast(witem d) {
        Node newNode = new Node(d, heail, heail.prev);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
        return;
    }

    public witem removeLast(){
        if (size == 0) return null;
        witem data = heail.prev.data;
        heail.prev.prev.next = heail;
        heail.prev=heail.prev.prev;
        size--;
        return data;
    }

    //about size

    @Override
    public int size(){
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

    @Override
    public witem get(int index){
        if(index+1>size())return null;//判断是否超出索引范围
        Listiterator iter =  iterator();
        while(iter.hasNext() && (index>0)) {
            iter.next();
            index--;
        }
        return (witem) iter.next();
    }

    public witem getRecursive(int index){
        return recursive(index,heail.next);
    }

    @Override
    public void printDeque(){
        Node current = heail.next;
        while(current.data != null){
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
        return;
    }

    //compare

    @Override
    public boolean equals(Object o){
        if(o instanceof LinkedListDeque){
            LinkedListDeque <witem> ohther = (LinkedListDeque) o;
            if(ohther.size() != size){
                return false;
            }
            Listiterator oitr = ohther.iterator();
            Listiterator titr=this.iterator();
            while(titr.hasNext()){
                witem t_item=(witem) titr.next();
                witem o_item=(witem) oitr.next();
                if(!(t_item.equals(o_item))) return false;

            }
            return true;
        }
        return false;
    }

    //helper method

    private witem recursive(int index,Node cur){
        if(cur==heail && index==0) return null;//防止索引超范围
        if (index == 0) return cur.data;
        cur = cur.next;
        return recursive(index-1, cur);
    }
}
