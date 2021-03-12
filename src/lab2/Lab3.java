package lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Lab3 {
    static int[][] scheme = {
            {0, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
//    static double[] P = {0.75, 0.84, 0.9099999999999999, 0.96, 0.9775, 0.99, 0.9936, 0.9964};
    static double[] P = {0.5, 0.6, 0.7, 0.8, 0.85, 0.9, 0.92, 0.94};
    static int t = 1000;
    static int k = 1;

    public static void main(String[] args) {
        if (t <= 0) {
            System.out.println("Час має бути більше 0.");
            System.exit(0);
        } else if (scheme.length != P.length) {
            System.out.println("Неправильно введені дані.");
            System.exit(0);
        } else if (scheme.length == 0) {
            System.out.println("Матриця порожня.");
            System.exit(0);
        }else if(k < 0 ){
            System.out.println("k має бути більше 0.");
            System.exit(0);
        }
        for (double v : P) {
            if (v > 1 || v <= 0) {
                System.out.println("P має бути від 0 до 1.");
                System.exit(0);
            }
        }

        generateP(scheme, P);
        double Psystem = lab2.Psystem;
        double Qsystem = (1 - Psystem);
        System.out.println("Psystem: " + Psystem);
        System.out.println("Qsystem: " + Qsystem);

        int Tsystem = calculateT(Psystem);
        System.out.println("T: " + Tsystem + " год");

        double Qreserved = (double) 1 / getFactorial(k + 1) * Qsystem;
        System.out.println("Q reserved system: " + Qreserved);

        double Preserved = 1 - Qreserved;
        System.out.println("P reserved system: " + Preserved);

        souAll(Psystem, Qsystem, Preserved, Qreserved , Tsystem);

        System.out.println("------------------------");
        double [] QreservedArray = generateQreserved(P);
        System.out.println("------------------------");
        double [] PreservedArray = generatePreserved(QreservedArray);
        System.out.println("------------------------");

        generateP(scheme, PreservedArray);

        double PresSystem = lab2.Psystem;
        double QresSystem = (1 - PresSystem);
        System.out.println("P reversed system: " + PresSystem);
        System.out.println("Q reversed system: " + QresSystem);
        souAll(Psystem, Qsystem, PresSystem, QresSystem, Tsystem);


    }



    private static void souAll(double p, double q, double pr, double qr, int t) {

        int Treserved = calculateT(pr);
        System.out.println("T reserved system: " + Treserved + " год");

        double Gq = qr/q;
        System.out.println("Gq: " + Gq);

        double Gp = pr/p;
        System.out.println("Gp: " + Gp);

        double Gt = (double) Treserved/t;
        System.out.println("Gt: " + Gt);
    }

    private static void generateP(int[][] scheme, double[] p) {
        ArrayList<ArrayList<Integer>> table = lab2.createAllPossibleWay(scheme);
        HashSet<ArrayList<Integer>> allState = lab2.calculateAllState(table, scheme);
        ArrayList<Double> Pstate = lab2.calculatePstate(allState, p);
    }

    private static double[] generatePreserved(double[] q) {
        double [] result = new double[q.length];
        for (int i = 0; i < q.length; i++) {
            result[i] = 1 - q[i];
            System.out.println("P reserved " + i + ": " + result[i]);

        }
        return  result;
    }

    private static double[] generateQreserved(double[] p) {
        double [] result = new double[p.length];
        for (int i = 0; i < p.length; i++) {
            result[i] = Math.pow(1 - p[i], k+1);
            System.out.println("Q reserved " + i + ": " + result[i]);
        }
        return result;
    }

    private static int calculateT(double P) {
        return (int) Math.round(1 / (-Math.log(P) / t));
    }

    private static int getFactorial(int f) {
        int result = 1;
        for (int i = 1; i <= f; i++) {
            result = result * i;
        }
        return result;
    }
}
