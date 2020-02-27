import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {

    /** @source StudentArrayDequeLauncher.java
     * Test the studenArrayDeque randomly.*/
    @Test    
    public void testArrayDeque() {
        StudentArrayDeque<Integer> sadq = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String messageStr = "";

        for (int i = 0; i < 1000; i++) {
            double numBetZeroOne = StdRandom.uniform();

            if (numBetZeroOne <= 0.25) {
                messageStr += "addFirst(" + i + ')' + '\n'; 
                sadq.addFirst(i);
                ads.addFirst(i);
                // assertEquals(messageStr, ads.get(0), sadq.get(0));
            } else if ((numBetZeroOne > 0.25) && (numBetZeroOne <= 0.5)) {
                messageStr += "addLast(" + i + ')' + '\n';
                sadq.addLast(i);
                ads.addLast(i);
                // assertEquals(messageStr, ads.get(ads.size() - 1), sadq.get(ads.size() - 1));
            } else if ((numBetZeroOne > 0.5) && (numBetZeroOne <= 0.75)) {
                if ((sadq.isEmpty()) || (ads.isEmpty())) {
                    continue;
                }
                messageStr += "removeFirst()" + '\n';
                Integer actual = sadq.removeFirst();
                Integer expected = ads.removeFirst();
                assertEquals(messageStr, expected, actual);
            } else {
                if ((sadq.isEmpty()) || (ads.isEmpty())) {
                    continue;
                }
                messageStr += "removeLast()" + '\n';
                Integer actual = sadq.removeLast();
                Integer expected = ads.removeLast();
                assertEquals(messageStr, expected, actual);
            }

        }

        System.out.println(messageStr);


    }

}

