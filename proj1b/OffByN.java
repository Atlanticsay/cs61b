/** @author  Atlantic */

public class OffByN implements CharacterComparator {

    private int offN = 0;
    public OffByN(int N) {
        offN = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (Math.abs(diff) == offN) {
            return true;
        }
        return false;
    }

}
