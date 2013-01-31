package bowerbird.common.parser;

import java.util.ArrayList;
import java.util.Arrays;
import bowerbird.common.item.ItemProperties;
import bowerbird.util.StringUtil;

public class ParseResult {

	public enum ParseResultCode {
		SUCCESS,
		FAILED_NO_STATE,
		FAILED_MISSING_FIELD;
	}
	
	private ParserState finalState;
	private ParseResultCode result;
	private ArrayList<String> unknownWords;
	private ArrayList<String> missingFields;
	private ItemProperties properties;
	private String input;
	
	public ParseResult(ParserState finalState,String input) {
		this.finalState = finalState;
		this.input = input;
		unknownWords = new ArrayList<String>();
		missingFields = new ArrayList<String>();
	}
	
	public ParserState finalState() {
		return finalState;
	}
	
	public ParseResultCode resultCode() {
		return result;
	}
	
	public void setResultCode(ParseResultCode code) {
		this.result = code;
	}
	
	public String input() {
		return input;
	}
	
	public ItemProperties properties() {
		return properties;
	}
	
	public void setProperties(ItemProperties properties) {
		this.properties = properties;
	}
	
	public ArrayList<String> missingFields() {
		return missingFields;
	}
	
	public void setMissingFields(ArrayList<String> missingFields) {
		this.missingFields = missingFields;
	}
	
	public void setLeftoverString(String left) {
		this.unknownWords = new ArrayList<String>(Arrays.asList(left.split(" ")));
		while(unknownWords.remove(" "));
		for(int i = unknownWords.size()-1; i >= 0; --i) {
			String s = unknownWords.get(i);
			if(StringUtil.isOnlyWhitespace(s) || StringUtil.isSinglePunctuation(s)) {
				unknownWords.remove(i);
			}
		}
		for(String s: this.unknownWords) {
			System.out.println("Unknown word " + s);
		}
	}
	
	public String toString() {
		if(resultCode()==ParseResultCode.SUCCESS) {
			return "Got " + properties().signature() + " for " + input;
		} else if(resultCode()==ParseResultCode.FAILED_NO_STATE) {
			return "Stuck at state: " + finalState + " for " + input;
		} else if(resultCode()==ParseResultCode.FAILED_MISSING_FIELD) {
			String ret = "Could not locate ";
			for(String s: missingFields) {
				ret+=s+",";
			}
			ret+=" for " + input;
			return ret;
		}
		return "UNKNOWN PARSE RESULT CODE " + result;
	}
	
}
