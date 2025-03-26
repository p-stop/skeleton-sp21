//package gh2;
//
//import deque.ArrayDeque;
//import deque.Deque;
//
//public class Harp{
//    private static final int SR=44100;
//    private static final double DECAY= .996;
//    private Deque<Double> buffer;
//    // 其他成员变量和构造函数
//    public Harp(double frequency) {
//        buffer = new ArrayDeque<Double>();
//        for (int i = 0; i < Math.round(SR/frequency)/2; i++) {
//            buffer.addLast((double)0);
//        }
//    }
//    public void pluck() {
//        for(int i = 0; i < buffer.size(); i++) {
//            buffer.addLast(Math.random()-0.5);
//            buffer.removeFirst();
//        }
//    }
//
//    public void tic() {
//        double oldValue = buffer.removeFirst();
//        double nextValue = buffer.get(0);
//        double newDouble = (oldValue + nextValue)/2*DECAY;
//        newDouble = -newDouble; // 翻转符号
//        buffer.addLast(newDouble);
//    }
//    public double sample() {
//        return buffer.get(0);
//    }
//}
