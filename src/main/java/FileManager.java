
import exceptions.FileDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileManager {
    public BigDecimal[][] readMatrixFromFile(String path) throws FileNotFoundException, FileDataException {
        Scanner scanner = new Scanner(new File(path));
        try {
             int rows=scanner.nextInt(); // <= 20
            int cols = rows + 1;
            BigDecimal[][] matrix = new BigDecimal[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = new BigDecimal(scanner.next());
                }
            }
            scanner.close();
            return matrix;
        }catch (Exception e)   {
            throw new FileDataException();
        }

    }

}

