package lab1;

import java.util.ArrayList;
import java.util.Arrays;

public class lab1 {
    static int[] selection1 = {18, 18, 18, 19, 21, 45, 45, 63,
            68, 92, 96, 106, 125, 164, 173, 181, 187, 214, 226, 245, 246, 246, 278, 281, 292, 295,
            296, 307, 313, 315, 435, 436, 444, 446, 455, 462, 466, 474, 508, 555, 578, 583, 605,
            607, 609, 623, 644, 659, 681, 694, 702, 711, 740, 793, 835, 854, 890, 901, 903, 1107,
            1118, 1122, 1146, 1187, 1216, 1247, 1280, 1334, 1386, 1386, 1390, 1396, 1424, 1426,
            1452, 1602, 1622, 1644, 1739, 1762, 1762, 1803, 1852, 1934, 1968, 2068, 2154, 2222,
            2234, 2352, 2425, 2491, 2895, 3483, 4211, 4465, 4467, 4539, 5159, 7443};
    static int[] selection = {766, 137, 105, 124, 63, 356, 67, 113, 325,
            10, 291, 271, 199, 90, 146, 461, 48, 305,
            150, 900, 640, 120, 23, 403, 36, 321, 102,
            136, 451, 257, 55, 87, 264, 135, 542, 425,
            54, 148, 188, 387, 133, 193, 524, 161, 179,
            558, 132, 139, 74, 23, 71, 140, 131, 168,
            159, 97, 31, 625, 34, 111, 452, 12, 26, 234,
            543, 252, 269, 10, 228, 143, 40, 120, 237,
            171, 16, 221, 55, 99, 105, 192, 213, 539, 7,
            89, 452, 161, 478, 85, 443, 32, 162, 633,
            249, 132, 283, 76, 548, 136, 322, 107};
    static double gama = 0.84;
    static int timeWork = 511;
    static int timeFailurs = 488;

    static double lengthOfInterval;
    static int index;

    public static void main(String[] args) {
        average();
        Arrays.sort(selection);
//        System.out.println(Arrays.toString(selection));
        ArrayList<ArrayList<Double>> intervalsArray = intervalBreakdown();
        double[] f = fiCalculate(intervalsArray);
        double[] P = PCalculate(f);
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
        result = 1 - sum;
        return result;
    }

    private static double percent(double[] p) {
        double result = 0;
        double d = 0;
        for (int i = 0; i < p.length-1; i++) {
            if(p[i] >= gama && p[i+1] <= gama){
                d = (p[i] - gama)/(p[i] - p[i+1]);
                result = i + lengthOfInterval*d;
                break;
            }
        }
//        System.out.println(d);
        System.out.println("Ty = " + result);
        return result;
    }

    private static double[] PCalculate(double[] f) {
        double[] result = new double[11];
        result[0] = 1.0;
        double sum = 0;
        for (int i = 0; i < f.length; i++) {
            sum += f[i] * lengthOfInterval;
            result[i + 1] = (1 - sum);

        }
//        System.out.println(Arrays.toString(result));
        return result;
    }

    private static double[] fiCalculate(ArrayList<ArrayList<Double>> intervalsArray) {
        double[] result = new double[10];
        for (int i = 0; i < intervalsArray.size(); i++) {
            result[i] = (double) intervalsArray.get(i).size() / (selection.length * lengthOfInterval);
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }

    private static ArrayList<ArrayList<Double>> intervalBreakdown() {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(new ArrayList<Double>());
        }
        int max = selection[selection.length - 1];
        lengthOfInterval = (double) max / 10;
        int step = 1;
        for (int i = 0; i < selection.length; i++) {
            if ((double) selection[i] <= lengthOfInterval * step) {
                result.get(step - 1).add((double) selection[i]);
            } else {
                step = (int) Math.ceil(selection[i]/lengthOfInterval);
                result.get(step - 1).add((double) selection[i]);
            }
        }
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i));
//        }
        return result;
    }

    private static void average() {
        int sum = 0;
        for (int j : selection) {
            sum += j;
        }
        double tsr = (double) sum / selection.length;
        System.out.println("Середній наробіток до відмови = " + tsr);
    }
}
