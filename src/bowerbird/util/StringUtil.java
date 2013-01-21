package bowerbird.util;

public class StringUtil {

	public static String removeWord(String s,String word) {
		return s.replace(word, "");
	}
	
	public static boolean isOnlyWhitespace(String s) {
		for(int i=0; i < s.length(); ++i) {
			if(!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
}
