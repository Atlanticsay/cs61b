import java.util.LinkedList;
import java.util.List;

public class MyTrieSet<Value> implements TrieSet61B {

    private static final int R = 256; // extended ASCII
    private Node root; // the root of the trie
    private int size; // no. of keys in the trie

    private static class Node {
        private Object val; // value associated with the key
        private boolean isKey; // the end of a prefix
        Node[] next; // point to the next node array of size R

        private Node(Object valueIn, boolean isKey) {
            val = valueIn;
            this.isKey = isKey;
            next = new Node[R];
        }

        private Node() {
            next = new Node[R];
            isKey = false;
        }
    }

    /** Initialize an empty string symbol table. */
    public MyTrieSet() {
        root = new Node();
        size = 0;
    }


    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        Node x = get(key, root, 0);
        return !(x == null);
    }

    /** Returns the value associated with the given key. */
    public Value get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        Node x = get(key, root, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.val;
    }

    private Node get(String key, Node node, int d) {
        if (node == null) {
            return null;
        }
        if (d == key.length()) {
            return node;
        }

        char c = key.charAt(d); //index of the next node
        return get(key, node.next[c], d + 1);
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        add(key, null, 0, root);
    }

    public Node add(String key, Value val, int d, Node node) {
        if (node == null) {
            node = new Node();
        }
        if (key.length() == d) {
            if (!node.isKey) {
                node.isKey = true;
            }
            node.val = val;
            size++;
            return node;
        }
        char c = key.charAt(d);
        node.next[c] = add(key, val, d + 1, node.next[c]);
        return node;
    }


    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node prefixNode = get(prefix, root, 0);
        if (prefixNode == null) {
            throw new IllegalArgumentException("The prefix does not exist.");
        }
        List<String> retList = new LinkedList<>();
        gatherKeys(retList, new StringBuilder(prefix), prefixNode);
        return retList;
    }

    // @source https://algs4.cs.princeton.edu/52trie/TrieST.java.html/
    private void gatherKeys(List<String> retList, StringBuilder prefix, Node curNode) {
        if (curNode == null) {
            return;
        }
        if (curNode.isKey) {
            retList.add(prefix.toString());
        }
        for (char d = 0; d < R; d++) {
            prefix.append(d);
            gatherKeys(retList, prefix, curNode.next[d]);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("The query string is null.");
        }
        int loc =  longestPrefixHelper(query, root, 0, -1);
        if (loc == -1) {
            return null;
        }
        return query.substring(0, loc);
    }

    private int longestPrefixHelper(String query, Node node, int d, int prefixLen) {
        if ((node == null)) {
            return prefixLen;
        }
        if (node.isKey) {
            prefixLen = d;
        }
        if (d == query.length()) {
            return prefixLen;
        }
        char c = query.charAt(d);
        prefixLen = longestPrefixHelper(query, node.next[c], d + 1, prefixLen);
        return prefixLen;
    }

}
