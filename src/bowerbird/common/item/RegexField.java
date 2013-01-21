package bowerbird.common.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.annotations.Expose;

public class RegexField {

	public enum RegexFieldType {
		STANDARD, STATIC, MULTIPLE 
	};
	
	private Pattern pattern;
	@Expose private ItemField field;
	@Expose private String staticText;
	@Expose private RegexFieldType type;
	@Expose private String delim;
	@Expose private String regex;
	
	public static RegexField createMultipleRegex(String field,String regex,String delim) {
		RegexField rf = new RegexField();
		rf.regex = regex;
		rf.field = new ItemField(field);
		rf.type = RegexFieldType.MULTIPLE;
		rf.delim = delim;
		return rf;
	}
	
	public static RegexField createStandardRegex(String field,String regex,boolean required) {
		RegexField rf = new RegexField();
		rf.regex = regex;
		rf.field = new ItemField(field);
		rf.field.required = required;
		rf.type = RegexFieldType.STANDARD;
		return rf;
	}
	public static RegexField createStaticRege(String field,String text) {
		RegexField rf = new RegexField();
		rf.staticText = text;
		rf.field = new ItemField(field);
		rf.type = RegexFieldType.STATIC;
		return rf;
	}
	
	public RegexField() {
		
	}
	
	public String getMatch(String text) {
		switch(type) {
			case STANDARD:
				return standardMatch(text);
			case STATIC:
				return staticText;
			case MULTIPLE:
				return multipleMatch(text);
		}
		
		return null;
	}
	
	public String standardMatch(String text) {
		Matcher m = pattern().matcher(text);
		System.out.println("Matching to " + pattern().pattern() + " - " + text + " count " + m.groupCount());
		
		if(m.find()) {
			return m.group();
		}
		return null;
	}
	
	public String multipleMatch(String text) {
		String ret = " ";
		Matcher m = pattern().matcher(text);
		while(m.find()) {
			ret+=m.group().trim()+delim;
		}
		if(ret.length() > 0) {
			ret = ret.substring(0, ret.length()-1);
		}
		return ret;
	}
	
	public ItemField field() {
		return this.field;
	}
	
	private Pattern pattern() {
		if(pattern==null) {
			pattern = Pattern.compile(regex);
		}
		return pattern;
	}
	
}
