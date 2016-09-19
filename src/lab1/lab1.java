package lab1;

import Jama.Matrix;
/**
 * Created by Anastasia on 19.09.2016.
 */
public class lab1 {
    //таблиця ймовірностей переходів між блоками алгоритму
    static double[][] prob = {
           //1  2  3  4  5  6  7  8  9  10
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, //1
            {0, 0, 0.5, 0.5, 0, 0, 0, 0, 0, 0}, //2
            {0, 0, 0, 0, 0.8, 0.2, 0, 0, 0, 0}, //3
            {0, 0, 0, 0, 0, 0, 0, 0.75, 0, 0.25}, //4
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //5
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, //6
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //7
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, //8
            {0, 0, 0, 0, 0, 0, 0, 0.75, 0, 0.25}, //9
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //10
    };
                           //0  1  2  3  4  5  6  7  8  9  10
    static double[] x = {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static double[] n = new double[10];
    static int amountOfFiles = 4;
    static double[][] klN = {
            //1  2   3  4   5  6  7   8   9   10
            {0, 28, 30, 0, 63, 0, 54, 0, 103, 0}, //k
            {110, 0, 0, 50, 0, 64, 0, 101, 0, 83}, //l
            {2, 0, 0, 3, 0, 4, 0, 1, 0, 2} //N
    };

    //підрахунок середнього числа звертання до файлу з номером numOfFile
   public static double amountOfRequest(int numOfFile){
        double Nh = 0;
        for (int i = 0; i < n.length; i++){
            if(klN[2][i] == numOfFile) {
                Nh += n[i];
            }
        }
        return Nh;
    }

    //підрахунок середньої кількості операцій при одному прогоні алгоритму
    public static double amountOfOperations(){
        double Q = 0;
        for (int i = 0; i < n.length; i++){
            Q += n[i]* klN[0][i];
        }
        return Q;
    }

    //підрахунок середньої кількості інформації, що передається при одному
    //звертанні до файлу з номером numOfFile
    public static double amountOfInfo(int numOfFile){
        double Qh = 0;
        for (int i = 0; i < n.length; i++){
            if(klN[2][i] == numOfFile) {
                Qh += n[i] * klN[1][i];
            }
        }
        Qh /=  amountOfRequest(numOfFile);
        return Qh;
    }

    //підрахунок середньої трудомісткості
    public static double laborContent(){
        double N = 0;
        for (int i = 0; i < n.length; i++){
          //  if(klN[0][i] != 0){
                N += n[i];
         //   }
        }
        return amountOfOperations()/ N;
    }

    public static void main(String[] args) {

        Matrix A = new Matrix(prob);
        Matrix temp = A.transpose();
        A = temp;
        for (int i =0; i< 10; i++)
            A.set(i, i, A.get(i, i) - 1);
        Matrix B = new Matrix(x, 10);
        //розрахунок коренів рівняння
        Matrix nM = A.solve(B);

        int j = 0;
        for (int i = 0; i < n.length; i++){
            n[i] = nM.get(i, j);
            System.out.println("n[" + (i + 1) + "] = " + n[i]);
        }
        System.out.println();
        System.out.println("Середнє число звертань до кожного з файлів");
        for (int i = 0; i < amountOfFiles; i++){
            System.out.println("\tФайл " + (i + 1) + " - " + amountOfRequest(i));
        }
        System.out.println();
        System.out.println("Середня кількість операцій при одному прогоні алгоритму\nQ = "
                + amountOfOperations());
        System.out.println();
        System.out.println("Середня кількість інформації, що передається\nпри одному звертанні " +
                "до кожного з файлів");
        for (int i = 0; i < amountOfFiles; i++){
            System.out.println("\tФайл " + (i + 1) + " - " + amountOfInfo(i));
        }
        System.out.println();
        System.out.println("Середня трудомісткість\nQ0 = "
                + laborContent());
        System.out.println();
    }
}
