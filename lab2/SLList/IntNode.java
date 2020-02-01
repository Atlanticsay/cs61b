public class IntNode {
    
    public int item; // an int number that will be put into the node
    public IntNode next; // the next node of the current node

    public IntNode(int i, IntNode n) {
        item = i;
        next = n;
    }
}