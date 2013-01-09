package bowerbird.common.item;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemProperties {

	private ArrayList<String> fields;
	private HashMap<String, String> values;
	
	public ItemProperties() {
		fields = new ArrayList<String>();
		values = new HashMap<String, String>();
	}
	
	public String signature() {
		String ret = new String();
		for(String s: fields) {
			ret+=getValue(s)+" ";
		}
		return ret;
	}
	
	public void addValue(String field,String value) {
		values.put(field, value);
		fields.add(field);
	}
	
	public String getValue(String field) {
		return values.get(field);
	}
	
}
