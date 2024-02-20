
import exceptions.DeterminantException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

public class MatrixManger {
    private int k;

    public void setK(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public  void printMatrix(BigDecimal[][] matrix) {
        int rows = matrix.length;
        int cols = rows + 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%15s", matrix[i][j].round(MathContext.DECIMAL32).toString()+" ");
            }
            System.out.println();
        }
    }

    public  BigDecimal[][] straightRunning(BigDecimal[][] matrix) throws DeterminantException{
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
                    throw new DeterminantException();
                }
                BigDecimal[] tmp= matrix[indexTmp];
                matrix[i+1]=matrix[i];
                matrix[i]=tmp;
                a=matrix[i][i];
                indexTmp++;
                setK(getK()+1);
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

        if (getDeterminant(matrix, k).compareTo(BigDecimal.ZERO)!=0){
            return matrix;
        }else {
            throw new DeterminantException();
        }
    }

    public  BigDecimal[] reverseRunning(BigDecimal[][] matrix) {

        int rows = matrix.length;
        int cols = rows + 1;
        BigDecimal[] res = new BigDecimal[rows];
        Arrays.fill(res, BigDecimal.ZERO);
        for (int i = rows - 1; i >= 0; i--) {
            BigDecimal x = matrix[i][i]; //это нужно занести в ответы
            BigDecimal b = matrix[i][cols - 1];
            for (int j = cols - 2; j > i; j--) { //это иксы справа

                b = b.subtract(matrix[i][j].multiply(res[j]));

            }
            x = b.divide(matrix[i][i], new MathContext(5));
//            System.out.println("x "+x+"b "+b);

            res[i] = x;
        }

        return res;

    }

    public  void discrepancyOutput(BigDecimal[][] matrix, BigDecimal[] x) {
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


    public   BigDecimal getDeterminant(BigDecimal[][] matrix, int k) {
        int rows=matrix.length;
        BigDecimal det = BigDecimal.valueOf(Math.pow(-1, k));
        for(int i=0;i<rows;i++){
            det=det.multiply(matrix[i][i]);
        }
        return det;
    }


    public BigDecimal[][] generateMatrix(int n){
        BigDecimal[][] matrix = new BigDecimal[n][n+1];
        for (int i =0; i<n;i++){
            for ( int j=0;j<=n;j++){
                matrix[i][j]=new BigDecimal(Math.random()*100, MathContext.DECIMAL32);
            }
        }
        return matrix;
    }
}
