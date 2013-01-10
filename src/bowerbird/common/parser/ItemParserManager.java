package bowerbird.common.parser;

public class ItemParserManager {

	private ParserState baseState;
	
	public ItemParserManager() {
		baseState = new ParserState();
		
		ParserState phoneState = new ParserState();
		phoneState.addTerm("phone", 1);
		baseState.addStateTransition(phoneState);
		
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
		
		phoneState.addStateTransition(cableState);
		phoneState.addStateTransition(caseState);
		phoneState.addStateTransition(iPhoneState);
	}
	
	public ParserState baseState() {
		return baseState;
	}
	
}
