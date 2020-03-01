import org.junit.Test;
import static org.junit.Assert.*;

/** @author Atlantic */

public class TestUnionFind {

    @Test
    public void testUnion() {
        String mes = "";
        UnionFind uf = new UnionFind(8);

        uf.union(1, 0);
        mes += "uf.union(0, 1)\n";
        assertEquals(mes, 2, uf.sizeOf(0));
        assertEquals(mes, 0, uf.parent(1));
        assertEquals(mes, 7, uf.count());

        uf.union(3, 2);
        mes += "uf.union(2, 3)\n";
        uf.union(5, 4);
        mes += "uf.union(4, 5)\n";
        uf.union(7, 6);
        mes += "uf.union(6, 7)\n";

        uf.union(2, 0);
        mes += "uf.union(0, 2)\n";
        assertEquals(mes, 4, uf.sizeOf(0));
        assertEquals(mes, 3, uf.count());

        uf.union(7, 5);
        mes += "uf.union(5, 7)\n";
        assertEquals(mes, 4, uf.sizeOf(0));
        assertEquals(mes, 4, uf.find(6));
        assertEquals(mes, 2, uf.count());
        uf.union(7, 1);
        mes += "uf.union(1, 7)\n";
        assertEquals(mes, 8, uf.sizeOf(0));
        assertEquals(mes, 0, uf.find(6));
        assertEquals(mes, 1, uf.count());
    }


}
