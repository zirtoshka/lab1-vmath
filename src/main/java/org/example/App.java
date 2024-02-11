package org.example;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        BigDecimal[][] matrix = new BigDecimal[0][];
        FileManager fileManager=new FileManager();
        ScannerManager scannerManager=new ScannerManager();
        MatrixManger matrixManger=new MatrixManger();
        System.out.println("what are you going to do? (file/console)");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine().toLowerCase().trim();
        switch (answer){
            case "file":
               matrix = fileManager.readMatrixFromFile("matrixdata");
                break;
            case "console":
                matrix= scannerManager.readMatrixFromConsole();
                break;
            default:
                System.out.println("ne ponel(");
        }
        matrixManger.printMatrix(matrix);
        System.out.println();
        matrix=matrixManger.straightRunning(matrix);
        BigDecimal[] res=matrixManger.reverseRunning(matrix);
        int k=0;
        for (BigDecimal i:res){
            System.out.printf("x%d = %s\n",  k, i.toString());  k++;      }
//        System.out.println("gggggg");
//todo validation from math point of view
    }
}