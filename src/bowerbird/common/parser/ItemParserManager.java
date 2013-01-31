package bowerbird.common.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import bowerbird.common.item.ItemField;
import bowerbird.common.item.RegexField;
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
		addRegex(RegexField.createStaticRegex("phone", "iPhone"),"1");
		addRegex(RegexField.createMultipleRegex("model", new String[]{"3GS","3G","4S","3","4","5"}, "/"),"2");
		addRegex(RegexField.createStaticRegex("brand", "Apple"),"3");
		addRegex(RegexField.createStandardRegex("model",new String[]{"3GS","3G","4S","3","4","5"},true),"4");
		addRegex(RegexField.createStandardRegex("size", new String[]{"[0-9]{1,3}GB"}, true),"5");
		
		baseState = new ParserState(BASESTATE_ID);
		allStates.put(baseState.id(), baseState);
		
		ParserState phoneState = new ParserState("1");
		phoneState.addTerm("phone");
		addState(phoneState, "0");
		

		
		ParserState caseState = new ParserState("2");
		caseState.addTerm("cover");
		caseState.addTerm("case");
		caseState.addRegexFieldId("1");
		caseState.addRegexFieldId("2");
		
		ParserState cableState = new ParserState("3");
		cableState.addTerm("cable");
		cableState.addRegexFieldId("2");
		
		ParserState iPhoneState = new ParserState("4");
		iPhoneState.addTerm("phone", 0.5f);
		iPhoneState.addRegexFieldId("3");
		iPhoneState.addRegexFieldId("4");
		iPhoneState.addRegexFieldId("5");
		
		
		addState(cableState, "1");
		addState(caseState, "1");
		addState(iPhoneState, "1");
	}
	
	public ParserState getState(String name) {
		ParserState ret = allStates.get(name);
		if(ret==null) {
			ret = kvStore.load(name, ParserState.class);
			allStates.put(name, ret);
			for(String s: ret.fieldIds()) {
				ret.addRegexField(getRegexField(s));
			}
			System.out.println("Loaded from redis " + name);
		}
		return ret;
	}
	
	public RegexField getRegexField(String id) {
		RegexField ret = kvStore.load(id, RegexField.class);
		assert ret!=null;
		return ret;
	}
	
	public ItemField getField(String id) {
		return null;
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
		for(String s: state.fieldIds()) {
			state.addRegexField(getRegexField(s));
		}
		allStates.put(state.id(), state);
		kvStore.save(state);
		kvStore.save(top);
	}

	public String addRegex(RegexField regex) {
		return this.addRegex(regex, generateId());
	}
	
	public String addRegex(RegexField regex,String id) {
		regex.setId(id);
		kvStore.save(regex);
		return id;
	}
	
	public void updateState(ParserState state) {
		allStates.put(state.id(), state);
		kvStore.save(state);
	}
	
}
