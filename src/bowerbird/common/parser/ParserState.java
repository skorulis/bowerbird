package bowerbird.common.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bowerbird.common.item.ItemProperties;

public class ParserState {

	private Pattern pattern;
	private ArrayList<ParserState> nextStates;
	private Map<String,ItemProperties> properties;
	private Map<String,Float> terms;
	
	public ParserState(Pattern pattern) {
		nextStates = new ArrayList<ParserState>();
		properties = new HashMap<String, ItemProperties>();
		terms = new HashMap<String, Float>();
		this.pattern = pattern;
	}
	
	public void generatePattern() {
		String regex = "(";
		Set<String> keys = terms.keySet();
		for(String s: keys) {
			regex+="s"+"|";
		}
		regex = regex.substring(0, regex.length()-1) + ")";
		this.pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	}
	
	public boolean isEndState() {
		return nextStates.size()==0;
	}
	
	public ItemProperties getProperties(String title) {
		return null;
	}
	
	public ParserState nextState(String title) {
		for(ParserState state: this.nextStates) {
			if(state.matches(title)) {
				System.out.println("Changed state to " + state);
				return state;
			}
		}
		return null;
	}
	
	public boolean matches(String title) {
		Matcher m = pattern.matcher(title);
		boolean found = m.find();
		return found;
	}
	
	public void addStateTransition(ParserState state) {
		nextStates.add(state);
	}
	
	public void addProperties(String key, ItemProperties prop) {
		properties.put(key.toLowerCase(), prop);
	}
	
	public String toString() {
		return pattern.pattern();
	}
}
