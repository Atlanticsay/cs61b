/** @author: Atlantic */

public class UnionFind {

    // parent[i] : The root of member i in its set.
    private int[] parent;   
    // size[i] : The weight (num of elements) in subtree rooted at i.
    private int[] size;
    // The number of disjointed sets in the collection.  
    private int count; 


    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
            size[i] = 1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        int elemLen = parent.length;
        if ((vertex < 0) && (vertex >= elemLen)) {
            throw new IllegalArgumentException("index" + vertex + "is not valid:\n" +
                      "Index should be between 0 and " + (elemLen - 1));
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {   
        validate(v1);
        return size[find(v1)];
    }

    /* Returns the total number of sets in the collection. */
    public int count() {
        return count;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        if (find(v1) == find(v2)) {
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        if(rootV1 == rootV2) return;

        if (size[v1] > size[v2]) {        
            // linked the root of smaller set V2 to V1
            size[rootV1] += size[rootV2];
            parent[rootV1] -= size[rootV2];
            parent[rootV2] = rootV1;
        } else {
            size[rootV2] += size[rootV1];
            parent[rootV1] = rootV2;
            parent[rootV2] -= size[rootV1];              
        }
        count--;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        // find the root of the vertex 
        int pRoot = vertex; 
        while(parent[pRoot] >= 0) {
            pRoot = parent[pRoot];
        }
        // path compression: make all the vertexes along the way have parents of the root.
        int q = vertex;
        while(q != pRoot) {
            int newq = parent[q];
            parent[q] = pRoot;
            q = newq;
        }
        return pRoot;
    }

}
