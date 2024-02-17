package org.example;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

public class App {
    public static void main(String[] args)  {
        BigDecimal[][] matrix = new BigDecimal[0][];
        BigDecimal[][] resMatrix = new BigDecimal[0][];
        FileManager fileManager = new FileManager();
        ScannerManager scannerManager = new ScannerManager();
        boolean check;
        try {
            while (true) {
                check=true;
                System.out.println("what are you going to do? (file/console/exit)");
                Scanner sc = new Scanner(System.in);
                String answer = sc.nextLine().toLowerCase().trim();
                switch (answer) {
                    case "file":
                        System.out.println("enter filename:");
                        String filename=sc.nextLine();
                        try {
                            matrix = fileManager.readMatrixFromFile(filename);
                        }catch (FileNotFoundException e){
                            System.out.println("I can't find this file((");
                            check=false;
                        }
                        break;
                    case "console":
                        matrix = scannerManager.readMatrixFromConsole();
                        break;
                    case "exit":
                        throw new ExitException();
                    default:
                        System.out.println("ne ponel(");
                }
                if(check){
                System.out.println("original matrix:");
                MatrixManger.printMatrix(matrix);
                MatrixManger.checkDeterminant(matrix);

                resMatrix = matrix.clone();
                resMatrix = MatrixManger.straightRunning(resMatrix);
                System.out.println("triangular matrix:");
                MatrixManger.printMatrix(resMatrix);

                BigDecimal[] res = MatrixManger.reverseRunning(resMatrix);
                System.out.println("vector of unknowns:");
                int k = 0;
                for (BigDecimal i : res) {
                    System.out.printf("x%d = %s\n", k, i.toString());
                    k++;
                }
                System.out.println();

                System.out.println("vector of residuals: ");
                MatrixManger.discrepancyOutput(matrix, res);}
            }

        } catch (ExitException e) {
            System.out.println("bye bye");
        }

//        System.out.println("gggggg");
//todo validation from math point of view
    }
}