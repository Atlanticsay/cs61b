/** Discussion 03 of CS61B.
 * @author Yun G
 */

public class SLList {
    private class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }


        private IntNode first;
        private int size;

        public void addFirst(int x) {
            first = new IntNode(x, first);
            size = 1;
        }

        public void insert(int item, int position) {
            
        }

    }
}