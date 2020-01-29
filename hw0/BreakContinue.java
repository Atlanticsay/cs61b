/** Print the special sum of an array.
 * @author Atlantic
 */
public class BreakContinue {
    public static void windowPosSum(int[] a, int n) {
      for(int i = 0; i < (a.length - 1); i++) {
          if(a[i] < 0) {
              continue;
          }
          int sumR = 0;
          if((a.length - (i+1)) >= 3) {
              for(int j = 0; j <= n; j++) {
                  sumR = sumR + a[i+j];
              }
          } else {
              for(int j = i; j < a.length; j++) {
                  sumR = sumR + a[j];
              }
          }
          a[i] = sumR;           
      }
    }
  
    public static void main(String[] args) {
      int[] a = {1, 2, -3, 4, 5, 4};
      int n = 3;
      windowPosSum(a, n);
  
      // Should print 4, 8, -3, 13, 9, 4
      System.out.println(java.util.Arrays.toString(a));
    }
  }