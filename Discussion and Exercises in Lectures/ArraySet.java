import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArraySet<T> implements Iterable<T> {

    private T[] items;
    private int size; 

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key.
     */
    public boolean contains(T x) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    /* Associates the specified value with the specified key in this map. 
       Throws an IllegalArgumentException if the key is null. */
    public void add(T x) {
        if (x == null) {
            throw new IllegalArgumentException("Cannot add null.");
        }
        if(this.contains(x)) {
            return;
        }
        items[size] = x;
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return this.size;
    }

    public static <Glerp> ArraySet<Glerp> of (Glerp... stuff) {
        ArraySet<Glerp> returnSet = new ArraySet<Glerp>();
        for (Glerp x : stuff) {
            returnSet.add(x);
        }
        return returnSet;
    }

    /** Write a method to return String. */
    @Override
    // public String toString() {
    //     StringBuilder returnString = new StringBuilder("{");
    //     for (T item: this) {
    //         returnString.append(item.toString());
    //         returnString.append(", ");
    //     }
    //     returnString.append("}");
    //     return returnString.toString();
    // }
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for(T x : this) {
            listOfItems.add(x.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    }

    /** Overrride methods of Object. */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == 0) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        ArraySet<T> o = (ArraySet<T>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (T item : this) {
            if (!o.contains(item)) {
                return false;
            }
        }
        return true;
    }

    // implement the Iterable<T> interface
    // it has the iterator() method
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;
        public ArraySetIterator() {
            this.wizPos = 0;
        }

        public boolean hasNext() {
             return wizPos < size;
        }

        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }




    public static void main(String[] args) {
        ArraySet<String> s = new ArraySet<>();
        // s.add(null);
        s.add("horse");
        s.add("fish");
        s.add("house");
        s.add("fish");        
        // System.out.println(s.contains("horse"));        
        // System.out.println(s.size());

        // In order to use this nice enhanced java loop,
        // the Arrayset have to implement the Iterable<T> interface,
        // and have the Iterator methods in the interface
        for(String i : s) {
            System.out.println(i);
        }

        // The ugly iterating method equivalent to the enhanced for loop.
        Iterator<String> aseer = s.iterator();
        while(aseer.hasNext()) {
            String i = aseer.next();
            System.out.println(i);
        }

        ArraySet<String> asetOfStrings = ArraySet.of("hi", "I'm", "here");
        System.out.println(asetOfStrings);


    }

    /* Also to do:
    1. Make ArraySet implement the Iterable<T> interface.
    2. Implement a toString method.
    3. Implement an equals() method.
    */
}