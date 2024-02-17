
import exceptions.DeterminantException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

public class MatrixManger {
    public static void printMatrix(BigDecimal[][] matrix) {
        int rows = matrix.length;
        int cols = rows + 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%7s", matrix[i][j].toString()+" ");
            }
            System.out.println();
        }
    }

    public static BigDecimal[][] straightRunning(BigDecimal[][] matrix) throws DeterminantException{
        /*todo Если в процессе исключения неизвестных, коэффициенты:
       todo 𝒂𝟏𝟏,𝒂𝟐𝟐𝟏,𝒂𝟑𝟑𝟐…. = 0 ,
                тогда необходимо соответственным образом переставить уравнения системы*/
        int rows = matrix.length;
        int cols = rows + 1;
        for (int i = 0; i < rows - 1; i++) {
            BigDecimal a = matrix[i][i];
            int indexTmp=i+1;
            while (a.compareTo(BigDecimal.ZERO)==0){
                if(indexTmp>=rows){
//                    System.out.println("ochen' ploho");
                    throw new DeterminantException();
                }
                BigDecimal[] tmp= matrix[indexTmp];
                matrix[i+1]=matrix[i];
                matrix[i]=tmp;
//                System.out.println("ploho");
                a=matrix[i][i];
                indexTmp++;
            }
            for (int n = i + 1; n < rows; n++) {
                BigDecimal b = matrix[n][i];
                BigDecimal k = b.divide(a, new MathContext(5));   //определили коэфф для строки i
                BigDecimal[] tmp = new BigDecimal[cols]; //будем работать со строкой n
                for (int j = 0; j < cols; j++) {// для каждого столбца
                    tmp[j] = matrix[n][j].subtract(k.multiply(matrix[i][j]));
                }
                matrix[n] = tmp;
            }
        }
        if (checkDeterminant(matrix)){
            return matrix;
        }else {
            throw new DeterminantException();
        }
    }

    public static BigDecimal[] reverseRunning(BigDecimal[][] matrix) {
        int rows = matrix.length;
        int cols = rows + 1;
        BigDecimal[] res = new BigDecimal[rows];
        Arrays.fill(res, BigDecimal.ZERO);
        for (int i = rows - 1; i >= 0; i--) {
            BigDecimal x = matrix[i][i]; //это нужно занести в ответы
            BigDecimal b = matrix[i][cols - 1];
            for (int j = cols - 2; j > i; j--) { //это иксы справа

                b = b.subtract(matrix[i][j].multiply(res[j]));
//                System.out.println("res[j] "+res[j]);
//                System.out.println(matrix[i][j]);
            }
            x = b.divide(matrix[i][i], new MathContext(5));
//            System.out.println("x "+x+"b "+b);

            res[i] = x;
        }

        return res;

    }

    public static void discrepancyOutput(BigDecimal[][] matrix, BigDecimal[] x) {
        int rows = matrix.length;
        int cols = rows + 1;
        for (int i = 0; i < rows; i++) {
//            System.out.println(i);
            BigDecimal r = new BigDecimal(0);
            for (int j = 0; j < cols - 1; j++) {
                r = r.add(matrix[i][j].multiply(x[j]));
            }
//            System.out.println( matrix[i][cols-1]);
            r=r.subtract(matrix[i][cols-1]);
            System.out.printf("r%d = %s\n", i, r.toString());
        }
    }


    private static boolean checkDeterminant(BigDecimal[][] matrix) {
        int rows=matrix.length;

        for(int i=0;i<rows;i++){
            if (matrix[i][i].equals(0)){
                return false;
            }
        }
        return true;
    }
}
