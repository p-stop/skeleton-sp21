import java.util.*;
public class homework {
    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        int i=1;
//        double d=1.0;
//        double result1=1.5*3+(++d);
//        double result2=1.5*3+d++;
//        i+=3/i+3;
//        System.out.printf("%f %f %d",result1,result2,i);
//        int x=1;
//        System.out.printf("%b %b %b",(true)&&(3>4),(x>0)||(x<0),(x>=0)||(x<0));
//        for(int i=0;i<1000;i++){
//            if(isPrime(i)){
//                System.out.println(i);
//            }
//        }
//        for(int i=1;i<7;i++){
//            for(int j=1;j<=i;j++){
//                System.out.print(j+" ");
//            }
//            System.out.println();
//        }
        int[][] matrix = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}