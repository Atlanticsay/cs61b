/** Find the maximum number in an array.
 * @author Atlantic
 */
public class Max {

    public static int maxArr(int[] m) {
        int maxNum = 0;
        for(int i = 0; i < m.length; i++) {
            if(m[i] > maxNum) {
                maxNum = m[i];
            }
        }
        return maxNum;
    }
    public static void main (String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.println(maxArr(numbers));
    }

}