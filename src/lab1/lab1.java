package lab1;

import java.util.ArrayList;
import java.util.Arrays;

public class lab1 {

    static int[] selection = {25, 25, 14, 26, 28, 19, 31, 18, 21, 16, 17, 16, 24, 18, 12, 24, 18, 28, 23, 21};
//    static int[] selection = {766, 137, 105, 124, 63, 356, 67, 113, 325,
//            10, 291, 271, 199, 90, 146, 461, 48, 305,
//            150, 900, 640, 120, 23, 403, 36, 321, 102,
//            136, 451, 257, 55, 87, 264, 135, 542, 425,
//            54, 148, 188, 387, 133, 193, 524, 161, 179,
//            558, 132, 139, 74, 23, 71, 140, 131, 168,
//            159, 97, 31, 625, 34, 111, 452, 12, 26, 234,
//            543, 252, 269, 10, 228, 143, 40, 120, 237,
//            171, 16, 221, 55, 99, 105, 192, 213, 539, 7,
//            89, 452, 161, 478, 85, 443, 32, 162, 633,
//            249, 132, 283, 76, 548, 136, 322, 107};
    static double gama = 0.84;
    static int timeWork = 20;
    static int timeFailurs = 488;

    static double lengthOfInterval;
    static int index;

    public static void main(String[] args) {
        if(selection.length == 0){
            System.out.println("Довжина масиву = 0");
            return;
        } else if(gama > 1 || gama < 0 ){
            System.out.println("gama повинна бути від 0 до 1");
            return;
        }else if( timeWork < 0 || timeFailurs < 0){
            System.out.println("Час не може бути менше 0");
            return;
        }


        average();
        Arrays.sort(selection);
        System.out.println(Arrays.toString(selection));
        System.out.println();
        ArrayList<ArrayList<Double>> intervalsArray = intervalBreakdown();
        System.out.println();
        System.out.println("Статистична щільність розподілу ймовірності відмови:");
        double[] f = fiCalculate(intervalsArray);
        System.out.println();
        System.out.println("Ймовірність безвідмовної роботи пристрою:");
        double[] P = PCalculate(f);
        System.out.println();
        double T = percent(P);
        double Ptime = pTime(f, timeWork);
        System.out.println("P("+ timeWork + ") = " + Ptime);
        double l = lCalculate(f);
        System.out.println("l("+ timeFailurs + ") = " + l);

    }

    private static double lCalculate(double[] f) {
        double P = pTime(f, timeFailurs);
        //        System.out.println(index);
//        System.out.println(result);
        return f[index]/P;
    }

    private static double pTime(double[] f, int time) {
        if(time > selection[selection.length - 1] || time < selection[0]){
            System.out.println("Час " + time + " виходить за межі вибірки");
            return 0;
        }
        double result;
        double sum = 0;
        int step = 1;
        for (int i = 0; i < f.length; i++) {
            if(time >= lengthOfInterval*step){
                sum += f[i]*lengthOfInterval;
                step++;
            }else{
                sum +=  f[i]*(time - lengthOfInterval*(step - 1));
                index = i;
                break;
            }
        }
        System.out.println(sum);
        result = (1 - sum);
        return result;
    }

    private static double percent(double[] p) {
        double result = 0;
        double d = 0;
        for (int i = 0; i < p.length-1; i++) {
            if(p[i] >= gama && p[i+1] <= gama){
//                System.out.println(i);
                d = ( gama - p[i])/(p[i+1] - p[i]);
                result = ((i)*lengthOfInterval + lengthOfInterval*d);
                break;
            }
        }
        System.out.println("d = " + (d));
        System.out.println("Ty = " + result);
        return result;
    }

    private static double[] PCalculate(double[] f) {
        double[] result = new double[6];
        result[0] = 1.0;
        double sum = 0;
        for (int i = 0; i < f.length; i++) {
            sum += (f[i] * lengthOfInterval);
            result[i + 1] = (1 - sum);

        }
        for (int i = 0; i < result.length; i++) {
            System.out.println("для " + (i) + "-го інтревалу P(" + i*lengthOfInterval + ") : "  + result[i]);
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }

    private static double[] fiCalculate(ArrayList<ArrayList<Double>> intervalsArray) {
        double[] result = new double[5];
        for (int i = 0; i < intervalsArray.size(); i++) {
//            System.out.println("intervalsArray.get(i).size() " + intervalsArray.get(i).size() );
//            System.out.println("selection.length " + selection.length);
//            System.out.println("lengthOfInterval " + lengthOfInterval);
            result[i] = ((double) intervalsArray.get(i).size() / (selection.length * lengthOfInterval));
        }
        for (int i = 0; i < result.length; i++) {
            System.out.println("для " + (i+1) + "-го інтревалу f" + (i+1) + ": "  + result[i]);
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }



    private static ArrayList<ArrayList<Double>> intervalBreakdown() {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result.add(new ArrayList<Double>());
        }
        int max = selection[selection.length - 1];
        lengthOfInterval = (double) max / 5;
        System.out.println("Довжина одного інтервалу буде дорівнювати " + lengthOfInterval);
        int step = 1;
        for (int i = 0; i < selection.length; i++) {
            if ((double) selection[i] <= lengthOfInterval * step) {
                result.get(step - 1).add((double) selection[i]);
            } else {
                step = (int) Math.ceil(selection[i]/lengthOfInterval);
                result.get(step - 1).add((double) selection[i]);
            }
        }
        System.out.println();
        System.out.println("Інтервали:");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(i+1 + "-й інтревал від " + i*lengthOfInterval + " до " + (i+1)*lengthOfInterval +
                    ":" + result.get(i));
        }
        return result;
    }

    private static void average() {
        int sum = 0;
        for (int j : selection) {
            if(j < 0){
                System.out.println("Час не може бути менше 0");
                System.exit(0);
            }
            sum += j;
        }
        double tsr = (double) sum / selection.length;
        System.out.println("Середній наробіток до відмови = " + tsr);
    }
}
