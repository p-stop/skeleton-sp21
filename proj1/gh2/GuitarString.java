package gh2;

// TODO: uncomment the following import once you're ready to start this portion
 import deque.Deque;
import deque.LinkedListDeque;
 import edu.princeton.cs.algs4.StdAudio;
 import edu.princeton.cs.algs4.StdDraw;

 import java.util.Random;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer array with zeros.
        buffer=new LinkedListDeque<>();
        for(int i=0; i<Math.round(SR/frequency); i++) {
            buffer.addLast((double) 0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        for(int i=0;i<buffer.size();i++) {
            buffer.addLast(Math.random()-0.5);
            buffer.removeFirst();
        }
        return;
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**

        double s1= buffer.get(0);
        double s2= buffer.get(1);
        buffer.addLast((s1+s2)/2*DECAY);
        buffer.removeFirst();

        return;
    }
//    public void tic() {
//        double oldValue = buffer.removeFirst();
//        double nextValue = buffer.get(0);
//        double newDouble = (oldValue + nextValue) * 1.0; // 无衰减
//        Random random = new Random();
//        if (random.nextDouble() < 0.5) {
//            newDouble = -newDouble; // 以 0.5 的概率翻转符号
//        }
//        buffer.addLast(newDouble);
//    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return buffer.get(0);
    }
//    public static void main(String[]arg){
//        StdAudio.play(0.333);
//    }
}
    // TODO: Remove all comments that say TODO when you're done.
//package gh2;
//
//import deque.ArrayDeque;
//import deque.Deque;
//
//public class GuitarString{
//    private static final int SR=44100;
//    private static final double DECAY= .996;
//    private Deque<Double> buffer;
//    // 其他成员变量和构造函数
//    public GuitarString(double frequency) {
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
