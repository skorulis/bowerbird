package bowerbird.common.parser;

import java.util.ArrayList;

import bowerbird.common.item.ItemProperties;

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
	
	public String toString() {
		if(resultCode()==ParseResultCode.SUCCESS) {
			return "Got " + properties().signature() + " for " + input;
		} else if(resultCode()==ParseResultCode.FAILED_NO_STATE) {
			return "Could not transition from " + finalState + " for " + input;
		} else if(resultCode()==ParseResultCode.FAILED_MISSING_FIELD) {
			String ret = "Could not locate ";
			for(String s: missingFields) {
				ret+=s+",";
			}
			return ret;
		}
		return "UNKNOWN PARSE RESULT CODE " + result;
	}
	
}
