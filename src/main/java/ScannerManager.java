
import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerManager {
    public BigDecimal[][] readMatrixFromConsole(){
        BigDecimal[][] matrix;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of the matrix n: ");
        int rows = sc.nextInt();
        int cols=rows+1;

        matrix= new BigDecimal[rows][cols];

        System.out.println("Enter matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = sc.nextBigDecimal();
            }
        }
        return matrix;
    }
}
