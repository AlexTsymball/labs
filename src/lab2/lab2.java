package lab2;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class lab2 {
    //        static int[][] scheme = {{0, 0}, {0, 0}};
//    static int[][] scheme = {
//            {0, 0, 1, 1, 1, 0},
//            {0, 0, 1, 1, 0, 1},
//            {0, 0, 0, 1, 1, 1},
//            {0, 0, 0, 0, 1, 1},
//            {0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0}
//    };
//    static double[] P = {0.66, 0.04, 0.55, 0.63, 0.86, 0.58};
    //    static double[] P = {0.66, 0.04, 0.55, 0.63, 0.86, 0.58, 0.7, 0.02};
//    static int[][] scheme = {
//            {0, 1, 1, 0, 0, 0, 0, 0},
//            {0, 0, 0, 1, 1, 0, 0, 0},
//            {0, 0, 0, 1, 0, 1, 0, 1},
//            {0, 0, 0, 0, 1, 1, 0, 1},
//            {0, 0, 0, 0, 0, 1, 1, 0},
//            {0, 0, 0, 0, 0, 0, 1, 1},
//            {0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0}
//           };
//    static double[] P = {0.5,0.6, 0.7, 0.8, 0.85, 0.9, 0.92, 0.94};
//        static double[] P = {0.5, 0.6};
    static int t = 10;
    public static double Psystem;

    public static void main(String[] args) {
        int[][] scheme = {
                {0, 0, 1, 1, 1, 0},
                {0, 0, 1, 1, 0, 1},
                {0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
        double[] P = {0.66, 0.04, 0.55, 0.63, 0.86, 0.58};
        if (t <= 0) {
            System.out.println("Час має бути більше 0.");
            System.exit(0);
        } else if (scheme.length != P.length) {
            System.out.println("Неправильно введені дані.");
            System.exit(0);
        } else if (scheme.length == 0) {
            System.out.println("Матриця порожня.");
            System.exit(0);
        }
        for (double v : P) {
            if (v > 1 || v <= 0) {
                System.out.println("P має бути від 0 до 1.");
                System.exit(0);
            }
        }


        ArrayList<ArrayList<Integer>> table = createAllPossibleWay(scheme);
        System.out.println("Всі можливі стани системи:");
        HashSet<ArrayList<Integer>> allState = calculateAllState(table, scheme);
        System.out.println("-------------------------------");
        ArrayList<Double> Pstate = calculatePstate(allState, P);
        System.out.println("-------------------------------");

        System.out.println("Psystem: " + Psystem);
//        double lambda = calculateLambda();
//        System.out.println("lambda: " + lambda);
//        double T = 1 / lambda;
//        System.out.println("T: " + T + " год");


    }
//
//    private static double calculateLambda() {
//        return -Math.log(Psystem) / t;
//    }

    public static HashSet<ArrayList<Integer>> calculateAllState(ArrayList<ArrayList<Integer>> table, int [][] scheme) {
        HashSet<ArrayList<Integer>> result = new HashSet<>();
        for (int i = 0; i < table.size(); i++) {
            ArrayList<Integer> state = new ArrayList<>(table.get(i));
            result.add(new ArrayList<>(state));
            addState(result, state, scheme);
        }
//        for (ArrayList<Integer> p : result) {
//            System.out.println(p);
//        }
        return result;

    }

    private static void addState(HashSet<ArrayList<Integer>> result, ArrayList<Integer> state, int [][] scheme) {
        for (int j = 0; j < scheme.length; j++) {
            if (!state.contains(j + 1)) {
                state.add(j + 1);
                ArrayList<Integer> ad = new ArrayList<>(state);
                Collections.sort(ad);
                result.add(ad);
                addState(result, state, scheme);
                state.remove(state.size() - 1);
            }
        }

    }

    public static ArrayList<Double> calculatePstate(HashSet<ArrayList<Integer>> allState, double [] P) {
        ArrayList<Double> result = new ArrayList<>();
        Psystem = 0;
        for (ArrayList<Integer> i : allState) {
            double Pstate = 1.0;
            for (int j = 0; j < P.length; j++) {
                if (i.contains(j + 1)) {
                    Pstate *= P[j];
                } else {
                    Pstate *= ((double) 1 - P[j]);
                }
            }
            result.add(Pstate);
            Psystem += Pstate;
//            System.out.println(i + " : Pstate = " + Pstate);
        }
        return result;
    }

    public static ArrayList<ArrayList<Integer>> createAllPossibleWay(int [][] scheme) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        Map<ArrayList<Integer>, Integer> resultMap = new HashMap<>();
        HashSet<Integer> dots = new HashSet<>();

        for (int i = 0; i < scheme.length; i++) {
            if (scheme.length != scheme[i].length) {
                System.out.println("Матриця має бути квадратна.");
                System.exit(0);
            }
            for (int j = 0; j < scheme[i].length; j++) {
                if (scheme[i][j] != 1 && scheme[i][j] != 0) {
                    System.out.println("Неправильний ввід суміжно матриці. Має бути або 0 або 1.");
                    System.exit(0);
                }
                if (i == 0) {
                    if (scheme[i][j] == 1) {
                        if (i == j) {
                            System.out.println("Неправильно введені дані.");
                            System.exit(0);
                        }
                        dots.add(i);
                        dots.add(j);
                        ArrayList<Integer> allWay = new ArrayList<>();

                        allWay.add(i + 1);
                        allWay.add(j + 1);
                        addPoint(result, allWay, j, dots, true, scheme);
                        if (count) {
                            result.add(new ArrayList<>(allWay));
                        }
                    } else {
                        boolean add = false;
                        if (j != 0) {
                            for (int k = 0; k < scheme[j].length; k++) {
                                if (scheme[j][k] == 1 && !dots.contains(j)) {
                                    if (k == j) {
                                        System.out.println("Неправильно введені дані.");
                                        System.exit(0);
                                    }
                                    add = true;
                                    dots.add(k);
                                    ArrayList<Integer> allWay = new ArrayList<>();
                                    allWay.add(j + 1);
                                    allWay.add(k + 1);
                                    addPoint(result, allWay, k, dots, true, scheme);
                                    if (count) {
                                        result.add(new ArrayList<>(allWay));
                                    }
                                }
                            }
                            if (add) {
                                dots.add(j);
                            }
                        }
                    }

                }

            }

        }
        for (int i = 0; i < scheme.length; i++) {
            if (!dots.contains(i)) {
                ArrayList<Integer> finish = new ArrayList<>();
                finish.add(i + 1);
                result.add(finish);
            }
        }

//        for (ArrayList<Integer> integers : result) {
//            resultMap.put(integers, integers.size());
//            System.out.println(integers);
//        }
//        result = findAllPosibleWay(resultMap);

        return result;
    }

    private static ArrayList<ArrayList<Integer>> findAllPosibleWay(Map<ArrayList<Integer>, Integer> resultMap) {
        Map<ArrayList<Integer>, Integer> sorted = resultMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        ArrayList<ArrayList<Integer>> keysArr = new ArrayList<>(sorted.keySet());
//        System.out.println(keysArr);
        for (int i = 0; i < keysArr.size() - 1; i++) {
            for (int l = i; l < keysArr.size() - 1; l++) {
//                System.out.println("l " + l);
//                System.out.println(keysArr.get(i));
//                System.out.println(keysArr.get(l + 1));
                int count = 0;
                for (int j = 0; j < keysArr.get(i).size(); j++) {
                    for (int k = 0; k < keysArr.get(l + 1).size(); k++) {
                        if (keysArr.get(i).get(j).equals(keysArr.get(l + 1).get(k))) {
                            count++;
                        }
                    }
                }
                if (keysArr.get(l + 1).size() - 1 == count && keysArr.get(i).size() != keysArr.get(l + 1).size()) {
//                    System.out.println(count);
//                    System.out.println(keysArr.get(l + 1).size() - 1 );
//                    System.out.println(keysArr.get(l + 1).size());
//                    System.out.println(l+1);
//                    System.out.println(keysArr.get(i));
//                    System.out.println(keysArr.get(l+1));
                    keysArr.remove(l + 1);
//                    System.out.println(keysArr);
                    l--;

                }
            }

        }
//        System.out.println(keysArr);
        return keysArr;
    }

    static boolean flag = true;
    static boolean count;

    private static void addPoint(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> way,
                                 int index, HashSet<Integer> dots, boolean b, int [][] scheme) {
        count = b;
        for (int i = 0; i < scheme[index].length; i++) {
            if (scheme[index][i] == 1) {

                way.add(i + 1);
                dots.add(i);
                flag = true;
                addPoint(result, way, i, dots, false, scheme);

                if (flag) {
                    result.add(new ArrayList<>(way));
                    flag = false;
                }
                way.remove(way.size() - 1);
            }
        }


    }
}
