
import exceptions.DeterminantException;
import exceptions.ExitException;
import exceptions.FileDataException;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        BigDecimal[][] matrix = new BigDecimal[0][];
        BigDecimal[][] resMatrix;
        FileManager fileManager = new FileManager();
        ScannerManager scannerManager = new ScannerManager();
        MatrixManger matrixManger=new MatrixManger();
        boolean check;
        try {
            while (true) {
                check = true;
                System.out.println("what are you going to do? (file[1]/console[2]/random[3]/exit[4])");
                Scanner sc = new Scanner(System.in);
                String answer = "";
                try {
                    answer = sc.nextLine().toLowerCase().trim();
                    matrixManger.setK(0);

                } catch (NoSuchElementException e) {
                    throw new ExitException();
                }
                if (answer.equals("file") || answer.equals("1")) {
                    System.out.println("enter filename:");
                    String filename = sc.nextLine();
                    try {
                        matrix = fileManager.readMatrixFromFile(filename);
                    } catch (FileNotFoundException e) {
                        System.out.println("I can't find this file((");
                        check = false;
                    } catch (FileDataException e) {
                        System.out.println("Your file consists wrong data");
                        check = false;
                    }

                } else if (answer.equals("random") || answer.equals("3")) {
                    System.out.println("enter n: ");
                    try {
                        int n = sc.nextInt();
                        matrix = matrixManger.generateMatrix(n);

                    }catch (InputMismatchException e){
                        System.out.println("n has to be int");
                        check = false;
                    }

                } else if (answer.equals("console") || answer.equals("2")) {
                    try {
                        matrix = scannerManager.readMatrixFromConsole();
                    } catch (InputMismatchException e) {
                        System.out.println("n has to be int");
                        check = false;
                    } catch (Exception e) {
                        System.out.println("I can understand only numbers((");
                        check = false;
                    }

                } else if (answer.equals("exit") || answer.equals("3")) {
                    throw new ExitException();
                } else {
                    System.out.println("ne ponel(");
                    check = false;
                }
                if (check) {
                    System.out.println("original matrix:");
                    matrixManger.printMatrix(matrix);

                    resMatrix = matrix.clone();
                    try {
                        resMatrix = matrixManger.straightRunning(resMatrix);
                        System.out.println("triangular matrix:");
                        matrixManger.printMatrix(resMatrix);

                        System.out.println();
                        System.out.println("Det matrix: "+matrixManger.getDeterminant(resMatrix, matrixManger.getK()).round(MathContext.DECIMAL32));

                        BigDecimal[] res = matrixManger.reverseRunning(resMatrix);
                        System.out.println("vector of unknowns:");

                        int j = 0;
                        for (BigDecimal i : res) {
                            System.out.printf("x%d = %s\n", j, i.toString());
                            j++;
                        }
                        System.out.println();

                        System.out.println("vector of residuals: ");
                        matrixManger.discrepancyOutput(matrix, res);
                    } catch (DeterminantException e) {
                        System.out.println("I can't do algorithm, determinant is 0");
                    }
                }
            }

        } catch (ExitException e) {
            System.out.println("bye bye");
        }

    }
}