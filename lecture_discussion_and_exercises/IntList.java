public class IntList {
    /**
     * First element of list.
     */
    public int first;
    /**
     * Remaining elements of list.
     */
    public IntList rest;

    /**
     * A List with first element f and the rest r.
     */
    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of a list using recursion. */
    public int size() {
        if (this.rest == null) {
            return 1;
        }
        return 1 + this.rest.size();  
    }

    /** Return the size of the list using no recursion. */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while(p != null) {
            totalSize ++;
            p = p.rest;
        }
        return totalSize;
    }

    /**Return the ith item of the list. */
//    public IntList iterativeGet(int i) {
//        IntList p = this;
//        for(int j = 0; j < i; j++) {
//            p = this.rest;
//        }
//        return p.first;
//    }
//    public IntList get(int i) {
//        if(i == 0) {
//            return this.first;
//        }
//        return this.rest.get(i-1);
//    }



    /**
     * A List with null rest, and first = 0.
     */
    public IntList() {
    /* NOTE: public IntList () { }  would also work. */
        this(0, null);
    }

}