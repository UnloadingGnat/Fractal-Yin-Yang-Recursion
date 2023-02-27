import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Find the minimum tiles for a rectangular floor that is to be tiled with square tiles.
 *
 * Data is input into the DATA3.txt file, and is to be separated by space
 *
 * The output file OUT3.txt will contain 5 lines, the minimum number of tiles necessary to exactly cover the W by L space.
 *
 * @author Jaavin
 * @date 2020/05/24
 */

public class rectangleTiles {

    /**
     * getMinTiles - keep splitting the square
     * @param width width of the floor
     * @param length length of the floor
     * @return minTiles  minimum number of tiles to exactly cover w x l area
     */
    private static int getMinTiles(int width, int length) {

        int minTiles = 0;

        // base case width and length is 0
        if (width != 0 && length != 0) {

            // does not add any tiles
            if (width % 2 == 0 && length % 2 == 0) {
                minTiles = getMinTiles(width/2, length/2);
            }

            // add {width} tiles of 1x1 squares in a row
            else if (width % 2 == 0 && length % 2 == 1) {
                minTiles = width + getMinTiles(width/2,length/2);
            }

            // add {length} tiles of 1x1 squares in a column
            else if (width % 2 == 1 && length % 2 == 0) {
                minTiles = length + getMinTiles(width/2,length/2);
            }

            // adds rows and columns for w/2 and l/2 tiles
            else if (width % 2 == 1 && length % 2 == 1) {
                minTiles =  width + length - 1 + getMinTiles(width/2 , length/2);
            }
        }
        return minTiles;
    }

    /**
     * Optimize by handling for powers of 2
     * @param width width of the floor
     * @param length length of the floor
     * @return minTiles  minimum number of tiles to exactly cover w x l area
     */
    private static int getMinTilesOptimized(int width, int length) {
        int minTiles = 0;

        // calculate log base 2 of the area
        double isSquare = Math.log(width * length) / Math.log(2);

        // check if is square/area is a whole number
        if ((int) Math.ceil(isSquare) == (int) Math.floor(isSquare)) {
            // power of 2 only needs 1 tile
            minTiles = 1;
        } else {
            // find minimum number of tiles
            minTiles = getMinTiles(width, length);
        }
        return minTiles;
    }

    /**
     * Main program
     * @param args command line args
     * @throws IOException signals an io error has occurred
     */
    public static void main(String[] args) throws IOException {
        // read input, and setup output
        Scanner sc = new Scanner(new FileReader("DATA3.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("OUT3.txt"));

        // while there are floors w x l
        while (sc.hasNextLine()) {
            // split floor dimensions
            String[] floorDimension = sc.nextLine().split(" ");
            pw.println(getMinTilesOptimized(Integer.parseInt(floorDimension[0]),Integer.parseInt(floorDimension[1])));
            pw.flush();
        }

        sc.close();
        pw.close();
    }
}
