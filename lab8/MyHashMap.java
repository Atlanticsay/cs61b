import java.util.*;

/**  @author Atlantic */

public class MyHashMap<K, V> implements Map61B<K, V> {

    public static class EntryKV<K, V> {
        private K key;
        private V val;

        public EntryKV(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }
        public V getVal() {
            return val;
        }
        public void setVal(V val) {
            this.val = val;
        }

    }

    private static final int INIT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private int sizeThreshold;
    private double loadFactor;
    private int size = 0;
    private LinkedList<EntryKV<K, V>>[] buckets;


    public MyHashMap() {
        sizeThreshold = (int) (DEFAULT_LOAD_FACTOR * INIT_CAPACITY);
        buckets = (LinkedList<EntryKV<K, V>>[]) new LinkedList[INIT_CAPACITY];
    }

    public MyHashMap(int initialSize){
        sizeThreshold = (int) (DEFAULT_LOAD_FACTOR * initialSize);
        buckets = (LinkedList<EntryKV<K, V>>[]) new LinkedList[initialSize];
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.loadFactor = loadFactor;
        sizeThreshold = (int) (loadFactor * initialSize);
        buckets = (LinkedList<EntryKV<K, V>>[]) new LinkedList[initialSize];
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        buckets = (LinkedList<EntryKV<K, V>>[]) new LinkedList[INIT_CAPACITY];
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }

        int idxHash = hash(key);
        if (buckets[idxHash] == null) {
            return false;
        }

        for (EntryKV<K, V> kv : buckets[idxHash]) {
            if(kv.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int idxHash = hash(key);
        LinkedList<EntryKV<K, V>> currentBucket = buckets[idxHash];
        if (currentBucket == null) {
            return null;
        }
        for (EntryKV<K, V> kv : currentBucket) {
            if(kv.getKey().equals(key)) {
                return kv.getVal();
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V val) {
        if (key == null) {
            throw new NoSuchElementException("The key is null.");
        }

        boolean exist = false;
        int idxHash = hash(key);
        if (buckets[idxHash] == null) {
            buckets[idxHash] = (LinkedList<EntryKV<K, V>>) new LinkedList();
        } else {
            for (EntryKV<K, V> kv : buckets[idxHash]) {
                if(kv.getKey().equals(key)) {
                    kv.setVal(val);
                    exist = true;
                }
            }
        }

        if (!exist) {
            EntryKV<K, V> EntryKV = new EntryKV<>(key, val);
            buckets[idxHash].add(EntryKV);
            size += 1;
            if (this.size > sizeThreshold) {
                resize();
            }
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> ks = new HashSet<>();
        for (LinkedList<EntryKV<K, V>> curBuc : buckets) {
            if (curBuc.size() == 0) {
                continue;
            }
            for (EntryKV<K, V> EntryKV : curBuc) {
                if (EntryKV.getKey() == null) {
                    continue;
                }
                ks.add(EntryKV.getKey());
            }
        }
        return ks;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
//        throw new UnsupportedOperationException();
        if (key == null) {
            return null;
        }

        int idxHash = hash(key);
        LinkedList<EntryKV<K, V>> currentBucket = buckets[idxHash];
        if (currentBucket == null) {
            return null;
        }

        V retVal = null;
        for (EntryKV<K, V> kv : currentBucket) {
            if(kv.getKey().equals(key)) {
                retVal = kv.getVal();
                currentBucket.remove(kv);
                break;
            }
        }
        return retVal;
    }

    /**
     * Removes the EntryKV for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
//        throw new UnsupportedOperationException();
        if (key == null) {
            return null;
        }

        int idxHash = hash(key);
        LinkedList<EntryKV<K, V>> currentBucket = buckets[idxHash];
        if (currentBucket == null) {
            return null;
        }

        V retVal = null;
        for (EntryKV<K, V> kv : currentBucket) {
            if(kv.getKey().equals(key) && kv.getVal().equals(value)) {
                retVal = kv.getVal();
                currentBucket.remove(kv);
                break;
            }
        }
        return retVal;
    }

    /** Returns a Iterator that iterates over the keys of MyHashMap. */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    /** Calculate the hash value (bucket index) for a given key. */
    public int hash(K key) {
        // @Source https://algs4.cs.princeton.edu/34hash/SeparateChainingHashST.java.html
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }

    /** Resize the set when it reaches the loadFactor. */
    private void resize() {
        int newSize = size * 2;
        sizeThreshold = (int) (newSize * loadFactor);

        LinkedList<EntryKV<K, V>>[] newBuc = (LinkedList<EntryKV<K, V>>[]) new LinkedList[newSize];
        for (LinkedList<EntryKV<K, V>> curBuc : buckets) {
            if (curBuc == null) {
                continue;
            }
            for (EntryKV<K, V> EntryKV : curBuc) {
                if (EntryKV.getKey() == null) {
                    continue;
                }
                int idxHash = hash(EntryKV.getKey());
                if (newBuc[idxHash] == null) {
                    newBuc[idxHash] = new LinkedList<EntryKV<K, V>>();
                }
                newBuc[idxHash].add(EntryKV);
            }
        }
    }

}

