package bowerbird.common.parser;

import bowerbird.common.item.ItemProperties;

public class ItemParser {

	private ParserState baseState;
	
	public ItemParser(ParserState baseState) {
		this.baseState = baseState;
	}
	
	public ParseResult parseTitle(String title) {
		ParserState state = baseState;
		ParserState nextState;
		String lowercase = title.toLowerCase();
		while(true) {
			nextState = state.nextState(lowercase);
			if(nextState!=null) {
				state = nextState;
				System.out.println("Moved to state " + state);
			} else {
				ParseResult result = state.getProperties(title);
				return result;
			}
		}
	}
	
	
	
}
