package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(SLList<Integer> Ns, SLList<Double> times, SLList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.getFirst();
            double time = times.getFirst();
            int opCount = opCounts.getFirst();
            double timePerOp = time / opCount * 1e6;
            Ns.go();
            times.go();
            opCounts.go();;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        SLList<Integer> Ns=new SLList<>();
        SLList <Double> times=new SLList<>();
        SLList<Integer> opCounts=new SLList<>();
        SLList<Integer>list = new SLList();
        int num=32000;
        for (int i = 0; i < num; i++) {
            list.addLast(i);
        }
        int j=0;
        Stopwatch test = new Stopwatch();
        for (int i = 0; i < num; i++) {
            list.getLast();
            if(i==((1<<j)*1000)-1)
            {
                j++;
                Ns.addLast(i+1);
                times.addLast(test.elapsedTime());
                opCounts.addLast(1000);
            }
        }
        printTimingTable(Ns,times,opCounts);
        return;
    }

}
