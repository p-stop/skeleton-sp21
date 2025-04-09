package bstmap;


import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable,V> implements Map61B<K,V> {

//    private class BSTIterator<V> implements Iterator {
//        private Node<K,V> now;
//        public BSTIterator() {
//            now = root;
//        }
//        public boolean hasLeft() {
//            return now.left != null;
//        }
//        public boolean hasRight() {
//            return now.right != null;
//        }
//        public Node<K,V> nLeft() {
//            return now.left;
//        }
//        public Node<K,V> nRight() {
//            return now.right;
//        }
//    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    class Node<K,V> {
        public K key;
        public V value;
        public Node<K,V> left;
        public Node<K,V> right;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    private Node<K,V> root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    public BSTMap(K key, V value) {
        root = new Node<>(key, value);
        size = 1;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        Node<K,V> node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                return true;
            }
            else if (cmp < 0) {
                node = node.left;
            }
            else {
                node = node.right;
            }
        }
        return false;
    }
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        else {
            Node<K,V> node = root;
            while (node != null) {
                int cmp = key.compareTo(node.key);
                if (cmp == 0) {
                    return node.value;
                }
                else if (cmp < 0) {
                    node = node.left;
                }
                else {
                    node = node.right;
                }
            }
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (root==null) {
            root = new Node<>(key, value);
            size += 1;
        }
        else {
            Node<K,V> p = root;
            puthelper(p, key, value);
        }
    }

    public void printInOrder() {
        if (root == null) {
            return;
        }
        else {
            Node<K,V> p = root;
            piohelper(p);
        }
    }

    private void piohelper(Node<K,V> node) {
        if (node == null) {
            return;
        }
        else {
            piohelper(node.left);
            System.out.print(node.key + " ");
            piohelper(node.right);
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        Node<K,V> p = root;
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            V v = p.value;
            size--;
            if(p.right == null) {
                root = p.left;
            }
            else {
                Node<K,V> alternative = findalter(p);
                p.value = alternative.value;
                p.key = alternative.key;
            }
            return v;
        }
        else if (cmp < 0) {
            return removehelperl(p, key);
        }
        else {
            return removehelperr(p, key);
        }
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private V removehelperl(Node<K,V> node, K key) {
        if (node.left == null) {
            return null;
        }
        int cmp = key.compareTo(node.left.key);
        if (cmp == 0) {
            V v = node.left.value;
            size--;
            if(node.right == null) {
                node.left=node.left.left;
            }
            else {
                Node<K,V> alternative = findalter(node);
                node.left.value = alternative.value;
                node.left.key = alternative.key;
            }
            return v;
        }
        else if (cmp < 0) {
            return removehelperl(node.left, key);
        }
        else {
            return removehelperr(node.right, key);
        }
    }

    private V removehelperr(Node<K,V> node, K key) {
        if (node.right == null) {
            return null;
        }
        int cmp = key.compareTo(node.right.key);
        if (cmp == 0) {
            V v = node.right.value;
            size--;
            if(node.left == null) {
                node.right=node.right.right;
            }
            else {
                Node<K,V> alternative = findalter(node);
                node.right.value = alternative.value;
                node.right.key = alternative.key;
            }
            return v;
        }
        else if (cmp < 0) {
            return removehelperl(node.left, key);
        }
        else {
            return removehelperr(node.right, key);
        }
    }

    private Node<K,V> findalter(Node<K,V> node) {
        Node<K,V> p = node.right;
        Node<K,V> prev = node;
        int prevri = 0;
        while (p.left != null) {
            p = p.left;
            if (prevri != 0)
                prev = prev.left;
            else
                prev = prev.right;
            prevri++;
        }
        if (prevri != 0)
            prev.left = p.right;
        else
            prev.right = p.right;
        return p;
    }

    private Node<K,V> puthelper(Node<K,V> p,K key, V value) {
        if (p == null) {
            size++;
            return new Node(key,value);
        }
        else {
            int cmp = key.compareTo(p.key);
            if(cmp > 0) {
                p.right = puthelper(p.right,key,value);
                return p;
            }
            else if(cmp < 0) {
                p.left = puthelper(p.left,key,value);
                return p;
            }
            else {
                return p;
            }
        }
    }
}
