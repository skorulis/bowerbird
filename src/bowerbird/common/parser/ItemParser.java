package bowerbird.common.parser;

public class ItemParser {

	private ItemParserManager manager;
	
	public ItemParser(ItemParserManager manager) {
		this.manager = manager;
	}
	
	public ParseResult parseTitle(String title) {
		ParserState state = manager.baseState();
		ParserState nextState;
		String lowercase = title.toLowerCase();
		while(true) {
			nextState = this.nextState(state, lowercase);
			if(nextState!=null) {
				state = nextState;
			} else {
				ParseResult result = state.getProperties(title);
				return result;
			}
		}
	}
	
	private ParserState nextState(ParserState state,String lowercase) {
		ParserState bestState = null;
		float bestValue = -1;
		for(String stateId: state.chlidren()) {
			ParserState next = manager.getState(stateId);
			float val = next.getValue(lowercase);
			if(val > bestValue) {
				bestState = next;
				bestValue = val;
			}
		}
		return bestState;
	}
	
	
	
}
