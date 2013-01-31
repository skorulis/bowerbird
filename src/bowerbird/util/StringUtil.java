package bowerbird.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
	
	public static boolean isSinglePunctuation(String s) {
		if(s.length() != 1) {
			return false;
		}
		char c = s.charAt(0);
		return !Character.isLetter(c) && Character.isDigit(c);
	}
	
	public static String regexFromTerms(Collection<String> terms) {
		if(terms.size()==0) {
			return "";
		}
		String regex = "(";
		for (String s : terms) {
			regex += s + "|";
		}
		regex = regex.substring(0, regex.length() - 1) + ")";
		return regex;
	}
}
