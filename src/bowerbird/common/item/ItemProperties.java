package bowerbird.common.item;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemProperties {

	private ArrayList<ItemField> fields;
	private HashMap<String, String> values;
	
	public ItemProperties() {
		fields = new ArrayList<ItemField>();
		values = new HashMap<String, String>();
	}
	
	public String signature() {
		String ret = new String();
		for(ItemField f: fields) {
			ret+=getValue(f.fieldName)+" ";
		}
		return ret;
	}
	
	public void addValue(ItemField field,String value) {
		values.put(field.fieldName, value);
		fields.add(field);
	}
	
	public ArrayList<String> missingFields() {
		ArrayList<String> ret = new ArrayList<String>();
		for(ItemField f:fields) {
			if(f.required && getValue(f.fieldName)==null) {
				ret.add(f.fieldName);
			}
		}
		return ret;
	}
	
	public ArrayList<ItemField> fields() {
		return fields;
	}
	
	public String getValue(String field) {
		return values.get(field);
	}
	
}
