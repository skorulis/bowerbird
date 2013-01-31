package bowerbird.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bowerbird.persistence.Persistable;
import bowerbird.util.StringUtil;

import com.google.gson.annotations.Expose;

public class RegexField implements Persistable {

	public enum RegexFieldType {
		STANDARD, STATIC, MULTIPLE 
	};
	
	private Pattern pattern;
	
	@Expose private String id;
	@Expose private ItemField field;
	@Expose private String staticText;
	@Expose private RegexFieldType type;
	@Expose private String delim;
	@Expose private ArrayList<String> terms;

	
	public static RegexField createMultipleRegex(String field,String[] terms,String delim) {
		RegexField rf = new RegexField(RegexFieldType.MULTIPLE);
		rf.terms.addAll(Arrays.asList(terms));
		rf.field = new ItemField(field);
		rf.delim = delim;
		return rf;
	}
	
	public static RegexField createStandardRegex(String field,String[] terms,boolean required) {
		RegexField rf = new RegexField(RegexFieldType.STANDARD);
		rf.terms.addAll(Arrays.asList(terms));
		rf.field = new ItemField(field);
		rf.field.required = required;
		return rf;
	}
	public static RegexField createStaticRegex(String field,String text) {
		RegexField rf = new RegexField(RegexFieldType.STATIC);
		rf.staticText = text;
		rf.field = new ItemField(field);
		return rf;
	}
	
	private RegexField() {
		terms = new ArrayList<String>();
	}
	
	public RegexField(RegexFieldType type) {
		this();
		this.type = type;
	}
	
	public String getMatch(String text) {
		switch(type) {
			case STANDARD:
				return standardMatch(text);
			case STATIC:
				return staticMatch(text);
			case MULTIPLE:
				return multipleMatch(text);
		}
		
		return null;
	}
	
	public String staticMatch(String text) {
		System.out.println("Static match " + text + " = " + staticText);
		return staticText;
	}
	
	public String standardMatch(String text) {
		Matcher m = pattern().matcher(text);
		String ret = null;
		
		if(m.find()) {
			ret = m.group();
		}
		System.out.println("Matching to " + pattern().pattern() + " - " + text + " count " + m.groupCount() + " = " + ret);
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
			pattern = Pattern.compile(generateRegex());
		}
		return pattern;
	}
	
	public String generateRegex() {
		return StringUtil.regexFromTerms(terms);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String id() {
		return id;
	}
	
}
