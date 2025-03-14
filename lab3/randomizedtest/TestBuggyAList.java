package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testNoPrimesInList(){
    AListNoResizing<Integer> list = new AListNoResizing<>();
    BuggyAList<Integer> buggyList = new BuggyAList<>();
    list.addLast(3);buggyList.addLast(3);
    assertEquals(list.removeLast(),buggyList.removeLast());
    }
  AListNoResizing<Integer> L = new AListNoResizing<>();
  BuggyAList<Integer> buggyList = new BuggyAList<>();
  @Test
  public void testPrimesInList(){
    int N = 50000;
    for (int i = 0; i < N; i += 1) {
      int operationNumber = StdRandom.uniform(0, 4);
      if (operationNumber == 0)
      {
        // addLast
        int randVal = StdRandom.uniform(0, 100);
        L.addLast(randVal);
//        System.out.println("addLast(" + randVal + ")");
        buggyList.addLast(randVal);
        assertEquals(buggyList.getLast(),L.getLast());
      } else if (operationNumber == 1)
      {
        // size
//        int size = L.size();
//        System.out.println("size: " + size);
        assertEquals(buggyList.size(),L.size());
      }
      else if (operationNumber == 2)
      {
        if (L.size() == 0||buggyList.size() == 0)
          continue;
//        System.out.println("removeLast(" + L.removeLast() + ")");
        assertEquals(buggyList.removeLast(),L.removeLast());
      }
      else if (operationNumber == 3)
      {
        if (L.size() == 0||buggyList.size() == 0)
          continue;
//        System.out.println("getLast(" + L.getLast() + ")");
        assertEquals(buggyList.getLast(),L.getLast());
      }
    }}
}
