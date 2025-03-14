package deque;

public class LinkedListDeque<witem> {
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
    public LinkedListDeque() {
        heail = new Node(null, heail, heail);
        size = 0;
    }
    public void addFirst(witem d) {
        Node newNode = new Node(d, heail.next, heail);
        heail.next = newNode;
        newNode.next.prev = newNode;
        return;
    }
    public witem removeFirst(){
        witem data = heail.next.data;
        heail.next=heail.next.next;
        heail.next.prev=heail;
        size--;
        return data;
    }
    public void addLast(witem d) {
        Node newNode = new Node(d, heail, heail.prev);
        newNode.prev.next = newNode;
        newNode.prev.prev = newNode;
        return;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void print(){
        Node current = heail.next;
        while(current.data != null){
            System.out.print(current.data + " ");
        }
        System.out.println();
        return;
    }

}
