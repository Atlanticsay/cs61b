import java.util.Set;
import java.util.Iterator;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key; // the tree is sorted by the key
        private V val; // associate data
        private Node left, right; // point to left and right subtrees
        private int size; // the number of nodes below current node

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    private Node root; // root of the BST

    /* Initialize an empty binary search tree map. */
    public BSTMap() {

    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }


    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if(x == null) {
            return 0;
        }
        return x.size;
    }

    /* Returns true if the BSTMAP is empty. */
    public boolean isEmpty() {
        return (size() == 0);
    }


    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (get(key) == null) {
            return false;
        }
        return true;
    }


    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key error: null.");
        }
        return get(key, root);
    }
    private V get(K key, Node x) {
        if(x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return get(key, x.right);
        } else if (cmp < 0) {
             return get(key, x.left);
        } else {
            return x.val;
        }
    }

    /* Associates the specified value with the specified key in this map.
    * @source https://algs4.cs.princeton.edu/32bst/BST.java.html */
    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public void put(K key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        if (val == null) {
            remove(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right =  put(x.right, key, val);
        } else if (cmp < 0) {
            x.left =  put(x.left, key, val);
        } else {
            x.val = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /* Prints out the BSTMap in order of increasing key. */
    public void printInOrder() {
        if (isEmpty()) {
            System.out.println(" ");
        }
        printBSTInOrder(root);
    }

    private void printBSTInOrder(Node x) {
        if (x == null) {
            System.out.println("");
        }
        if ((x.left == null) && (x.right != null)) {
            printNode(x);
            printBSTInOrder(x.right);
            return;
        } else if ((x.right == null) && (x.left != null)) {
            printBSTInOrder(x.left);
            printNode(x);
            return;
        } else if ((x.right == null) && (x.left == null)) {
            printNode(x);
            return;
        } else if ((x.right != null) && (x.left != null)) {
            printBSTInOrder(x.left);
            printNode(x);
            printBSTInOrder(x.right);
        }
    }

    private void printNode(Node x) {
        System.out.print("(" + x.key + ", " + x.val + ")" + " ");
        System.out.println();
    }


    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        V retVal = get(key);
        root = delete(root, key);
        return retVal;
    }

    private Node delete(Node x, K key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = delete(x.right, key);
        } else if (cmp < 0) {
            x.left = delete(x.left, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            // two children nodes
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /* Delete the node with smallest key value for a given BSTMap. */
    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /* Return the node with smallest key value for a given BSTMap. */
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
