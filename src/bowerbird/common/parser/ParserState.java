package bowerbird.common.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bowerbird.common.item.ItemProperties;
import bowerbird.common.item.RegexField;
import bowerbird.common.parser.ParseResult.ParseResultCode;

public class ParserState {

	private Pattern pattern;
	private ArrayList<ParserState> nextStates;
	private Map<String,ItemProperties> properties;
	private Map<String,Float> terms;
	private ArrayList<RegexField> regexFields;
	
	public ParserState() {
		nextStates = new ArrayList<ParserState>();
		properties = new HashMap<String, ItemProperties>();
		terms = new HashMap<String, Float>();
		regexFields = new ArrayList<RegexField>();
	}
	
	public void generatePattern() {
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
		for(RegexField rf : this.regexFields) {
			String value =  rf.getMatch(title);
			props.addValue(rf.field(), value);
		}
		ret.setResultCode(ParseResultCode.SUCCESS);
		ret.setProperties(props);
		return ret;
	}
	
	public ParserState nextState(String title) {
		ParserState bestState = null;
		float bestValue = -1;
		for(ParserState state: this.nextStates) {
			float val = state.getValue(title);
			if(val > bestValue) {
				bestState = state;
				bestValue = val;
			}
		}
		return bestState;
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
	
	public String toString() {
		return pattern().pattern();
	}
	
	public void addStateTransition(ParserState state) {
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
	
	public void addRegexField(String field,String regex) {
		regexFields.add(RegexField.createStandardRegex(field, regex));
	}
	
	public void addRegexFieldMultiple(String field,String regex,String delim) {
		regexFields.add(RegexField.createMultipleRegex(field, regex, delim));
	}
}
