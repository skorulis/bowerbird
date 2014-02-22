package bowerbird.gpb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bowerbird.gpb.BowerbirdMessageProtocolGPB.ItemField;
import bowerbird.gpb.BowerbirdMessageProtocolGPB.RegexField;
import bowerbird.gpb.BowerbirdMessageProtocolGPB.RegexField.RegexFieldType;
import bowerbird.util.StringUtil;

public class RegexFieldHelper {

	public static RegexField createMultipleRegex(String field,String[] terms,String delim) {
		return RegexField.newBuilder().
		setType(RegexFieldType.MULTIPLE).
		setField(ItemFieldHelper.makeField(field)).
		setDelim(delim).addAllTerms(Arrays.asList(terms))
		.buildPartial();
	}
	
	public static RegexField createStandardRegex(String field,String[] terms,boolean required) {
		return RegexField.newBuilder().
				setType(RegexFieldType.STANDARD).
				setField(ItemFieldHelper.makeField(field,required)).
				addAllTerms(Arrays.asList(terms)).
				buildPartial();
	}
	public static RegexField createStaticRegex(String field,String text) {
		return RegexField.newBuilder().
				setType(RegexFieldType.STATIC).
				setField(ItemFieldHelper.makeField(field)).
				setStaticText(text).
				buildPartial();
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
		return staticText;
	}
	
	public String standardMatch(String text) {
		Matcher m = pattern().matcher(text);
		String ret = null;
		
		if(m.find()) {
			ret = m.group();
		}
		System.out.println("Matching to " + pattern().pattern() + " - " + text + " count " + m.groupCount() + " = " + ret);
		return ret;
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
