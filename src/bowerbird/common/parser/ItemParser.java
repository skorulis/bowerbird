package bowerbird.common.parser;

import java.util.regex.Pattern;

import bowerbird.common.item.ItemProperties;

public class ItemParser {

	private ParserState baseState;
	
	public ItemParser() {
		baseState = new ParserState(null);
		
		ParserState caseState = state("(case|cover)");
		baseState.addStateTransition(caseState);
		baseState.addStateTransition(state("cable"));
		
	}
	
	private ParserState state(String regex) {
		Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE); 
		return new ParserState(p);
	}
	
	
	public ItemProperties parseTitle(String title) {
		ParserState state = baseState;
		while(true) {
			state = state.nextState(title);
			if(state==null) {
				System.out.println("Could not find properties for " + title);
				return null;
			} else if(state.isEndState()) {
				return state.getProperties(title);
			} 
		}
	}
	
	
	
}
