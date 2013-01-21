package bowerbird.common.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.annotations.Expose;

import bowerbird.common.item.ItemProperties;
import bowerbird.common.item.RegexField;
import bowerbird.common.parser.ParseResult.ParseResultCode;
import bowerbird.persistence.Persistable;
import bowerbird.util.StringUtil;

public class ParserState implements Persistable {

	@Expose private String id;
	@Expose private String parent;
	private Pattern pattern;
	@Expose private ArrayList<String> nextStates;
	@Expose private Map<String,ItemProperties> properties;
	@Expose private Map<String,Float> terms;
	@Expose private ArrayList<RegexField> regexFields;
	
	public ParserState(String id) {
		this.id = id;
		nextStates = new ArrayList<String>();
		properties = new HashMap<String, ItemProperties>();
		terms = new HashMap<String, Float>();
		regexFields = new ArrayList<RegexField>();
	}
	
	public void generatePattern() {
		if(terms.size()==0) {
			this.pattern = Pattern.compile("");
			System.out.println("Missing terms for " + id());
			return;
		}
		String regex = "(";
		Set<String> keys = terms.keySet();
		for(String s: keys) {
			regex+=s+"|";
		}
		regex = regex.substring(0, regex.length()-1) + ")";
		this.pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	}
	
	public ParseResult getProperties(String title) {
		ParseResult ret = new ParseResult(this,title);
		if(this.regexFields.size()==0) {
			ret.setResultCode(ParseResultCode.FAILED_NO_STATE);
			return ret;
		}
		
		ItemProperties props = new ItemProperties();
		String left = new String(title);
		for(RegexField rf : this.regexFields) {
			String value =  rf.getMatch(title);
			props.addValue(rf.field(), value);
			left = StringUtil.removeWord(left, value);
		}
		ret.setLeftoverString(left);
		ArrayList<String> missing = props.missingFields();
		if(missing.size()==0) {
			ret.setResultCode(ParseResultCode.SUCCESS);
		} else {
			ret.setResultCode(ParseResultCode.FAILED_MISSING_FIELD);
			ret.setMissingFields(missing);
		}
		
		ret.setProperties(props);
		return ret;
	}
	
	public float getValue(String title) {
		float ret = 0;
		Matcher m = pattern().matcher(title);
		while(m.find()) {
			float val = terms.get(m.group()).floatValue();
			ret+=val;
		}
		return ret;
	}
	
	public boolean matches(String title) {
		Matcher m = pattern().matcher(title);
		boolean found = m.find();
		return found;
	}
	
	public Pattern pattern() {
		if(pattern==null) {
			generatePattern();
		}
		return pattern;
	}
	
	public String id() {
		return id;
	}
	
	public String parent() {
		return parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String toString() {
		return pattern().pattern();
	}
	
	public ArrayList<String> chlidren() {
		return nextStates;
	}
	
	public void addStateTransition(String state) {
		nextStates.add(state);
	}
	
	public void addProperties(String key, ItemProperties prop) {
		properties.put(key.toLowerCase(), prop);
	}
	
	public void addTerm(String term,float value) {
		terms.put(term, value);
	}
	
	public void addRegexField(RegexField rf) {
		regexFields.add(rf);
	}
	
	public void addRegexFieldStatic(String field,String regex) {
		regexFields.add(RegexField.createStaticRege(field, regex));
	}
	
	public void addRegexField(String field,String regex,boolean required) {
		regexFields.add(RegexField.createStandardRegex(field, regex, required));
	}
	
	public void addRegexFieldMultiple(String field,String regex,String delim) {
		regexFields.add(RegexField.createMultipleRegex(field, regex, delim));
	}
	
}
