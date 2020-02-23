/** @author Atlantic*/

public class Palindrome {

    /** Get each character of a given string.
     * Return a deque of characters*/
    public Deque<Character> wordToDeque(String word) {

        Deque<Character> wordDq = new ArrayDeque<Character>();
        for (int pos = 0; pos < word.length(); pos++) {
            char c = word.charAt(pos);
            wordDq.addLast(c);
        }
        return wordDq;
    }

    /** Determine whether a string is a Palindrome. */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDq = wordToDeque(word);
        int s = wordDq.size();
        if (wordDq.size() <= 1) {
            return true;
        }
        return isPalindromeHelper(wordDq);
    }

    /** Helper method of isPalindrome. */
    public boolean isPalindromeHelper(Deque<Character> wordDq) {
        if (wordDq.size() <= 1) {
            return true;
        }
        Character firstCur = wordDq.removeFirst();
        Character lastCur = wordDq.removeLast();
        if (!firstCur.equals(lastCur)) {
            return false;
        }
        return isPalindromeHelper(wordDq);
    }

    /** Determine whether a string is a of-by-one Palindrome. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDq = wordToDeque(word);
        int s = wordDq.size();
        if (wordDq.size() <= 1) {
            return true;
        }
        return isPalindromeHelper(wordDq, cc);
    }

    /** Helper method of isPalindrome. */
    public boolean isPalindromeHelper(Deque<Character> wordDq, CharacterComparator cc) {
        if (wordDq.size() <= 1) {
            return true;
        }
        if (!cc.equalChars(wordDq.removeFirst(), wordDq.removeLast())) {
            return false;
        }
        return isPalindromeHelper(wordDq, cc);
    }

}
