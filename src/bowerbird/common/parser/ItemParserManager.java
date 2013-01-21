package bowerbird.common.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import bowerbird.persistence.KVStore;

public class ItemParserManager {

	private static final String BASESTATE_ID = "0";
	
	private ParserState baseState;
	private Map<String, ParserState> allStates;
	private KVStore kvStore;
	
	public ItemParserManager(KVStore kvStore) {
		this.kvStore = kvStore;
		allStates = new HashMap<String, ParserState>();
		baseState = kvStore.load(BASESTATE_ID, ParserState.class);
		if(baseState == null) {
			buildTestParser();
		}
	}
	
	private void buildTestParser() {
		baseState = new ParserState(BASESTATE_ID);
		allStates.put(baseState.id(), baseState);
		
		ParserState phoneState = new ParserState("1");
		phoneState.addTerm("phone", 1);
		
		ParserState caseState = new ParserState("2");
		caseState.addTerm("cover", 1);
		caseState.addTerm("case", 1);
		caseState.addRegexFieldStatic("phone","iPhone");
		caseState.addRegexFieldMultiple("model", "( |/)(3GS|3G|4S|3|4|5)", "/");
		
		ParserState cableState = new ParserState("3");
		cableState.addTerm("cable", 1);
		cableState.addRegexFieldMultiple("model", "( |/)(3GS|3G|4S|3|4|5)", "/");
		
		ParserState iPhoneState = new ParserState("4");
		iPhoneState.addTerm("phone", 0.5f);
		iPhoneState.addRegexFieldStatic("brand","Apple");
		iPhoneState.addRegexField("model","iPhone (3GS|3G|4S|3|4|5)",true);
		iPhoneState.addRegexField("size", "[0-9]{1,3}GB",true);
		
		addState(phoneState, "0");
		addState(cableState, "1");
		addState(caseState, "1");
		addState(iPhoneState, "1");
	}
	
	public ParserState getState(String name) {
		ParserState ret = allStates.get(name);
		if(ret==null) {
			ret = kvStore.load(name, ParserState.class);
			allStates.put(name, ret);
			System.out.println("Loaded from redis " + name);
		}
		return ret;
	}
	
	public String generateId() {
		return UUID.randomUUID().toString();
	}
	
	public ParserState baseState() {
		return baseState;
	}
	
	//Methods coming from API calls
	
	public void addState(ParserState state,String under) {
		ParserState top = allStates.get(under);
		top.addStateTransition(state.id());
		state.setParent(under);
		allStates.put(state.id(), state);
		kvStore.save(state);
		kvStore.save(top);
	}
	
	public void updateState(ParserState state) {
		allStates.put(state.id(), state);
		kvStore.save(state);
	}
	
}
