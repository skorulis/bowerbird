package bowerbird.common.parser;

import bowerbird.common.item.ItemProperties;

public class ItemParser {

	private ParserState baseState;
	
	public ItemParser() {
		baseState = new ParserState();
		
		ParserState caseState = new ParserState();
		caseState.addTerm("cover", 1);
		caseState.addTerm("case", 1);
		caseState.addRegexField("phone","iPhone");
		caseState.addRegexFieldMultiple("model", "( |/)(3GS|3G|4S|3|4|5)", "/");
		
		ParserState cableState = new ParserState();
		cableState.addTerm("cable", 1);
		cableState.addRegexFieldMultiple("model", "( |/)(3GS|3G|4S|3|4|5)", "/");
		
		ParserState iPhoneState = new ParserState();
		iPhoneState.addTerm("phone", 0.5f);
		iPhoneState.addRegexField("brand","Apple");
		iPhoneState.addRegexField("model","iPhone (3GS|3G|4S|3|4|5)");
		iPhoneState.addRegexField("size", "[0-9]{1,3}GB");
		
		baseState.addStateTransition(cableState);
		baseState.addStateTransition(caseState);
		baseState.addStateTransition(iPhoneState);
	}
	
	public ItemProperties parseTitle(String title) {
		ParserState state = baseState;
		String lowercase = title.toLowerCase();
		while(true) {
			state = state.nextState(lowercase);
			if(state==null) {
				System.out.println("Could not find properties for " + title);
				return null;
			} else if(state.isEndState()) {
				return state.getProperties(title);
			} else {
				System.out.println("Moved to state " + state);
			}
		}
	}
	
	
	
}
