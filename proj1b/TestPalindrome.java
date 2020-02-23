import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        // test the normal isPalindrome method
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("non"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("Aa"));
        assertFalse(palindrome.isPalindrome("aaab"));
        assertTrue(palindrome.isPalindrome(""));

        // test the off-by-one isPalindrome method
        CharacterComparator ccByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("ab", ccByOne));
        assertTrue(palindrome.isPalindrome("flake", ccByOne));
        assertFalse(palindrome.isPalindrome("fLake", ccByOne));
        assertTrue(palindrome.isPalindrome("&&%", ccByOne));

    }

}
