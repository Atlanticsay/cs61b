/** Draw a triangle of which the size is determined by nRows.
 * @author Atlantic
 */
public class DrawTriangle {

    /** Draw a triangle.     * 
     * @param nRows determines the size (rows) of the triangle
     */
    public static void drawTriangle(int nRows) {
        int rowi = 1;
        while(rowi <= nRows) {
            for(int i = 1; i <= rowi; i++) {
                System.out.print("*");
            }
            System.out.println("");
            rowi ++;
        }
    }

    public static void main(String[] args) {
        int nRows = 10;
        drawTriangle(nRows);
    }
}