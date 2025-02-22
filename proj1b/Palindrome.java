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
    private boolean isPalindromeHelper(Deque<Character> wordDq) {
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
    private boolean isPalindromeHelper(Deque<Character> wordDq, CharacterComparator cc) {
        if (wordDq.size() <= 1) {
            return true;
        }
        if (!cc.equalChars(wordDq.removeFirst(), wordDq.removeLast())) {
            return false;
        }
        return isPalindromeHelper(wordDq, cc);
    }
    
    public static List<String> getWords(String inputFileName) {
    	List<String> lst = new ArrayList<String>();
    	In in = new In();
    	while(!in.isEmpty()) {
    		lst.add(in.readString());
    	}
    	return lst;
    }

    public static int countUniqueWords(List<String> words) {
    	Set<String> ss = new HashSet<>();
    	for (String s : word) {
    		ss.add(s);
    	}
    	return ss.size();



    	public static boolean isBST(TreeNode T) {
    		return isBSTHelper(T);
    	}
    	private static boolean isBSTHelper(TreeNode T) {
    		valueT = T.value;
    		valueL = T.left.value;
    		valueR = T.right.value;
    		boolean isLeftBST = true;
    		boolean isRightBST = true;

    		if (valueL != null) {
    			if (valueL > valueT) {
    				return false;
    			}
    			isLeftBST = isBSTHelper(T.left);
    		}
    		if (valueR != null) {
    			if (valueR < valueT) {
    				return false;
    			}
    			isRightBST = isBSTHelper(T.right);
    		}
    		return (isLeftBST && isRightBST);
    	}


    }








}
