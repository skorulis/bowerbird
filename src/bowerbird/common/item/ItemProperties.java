package bowerbird.common.item;

import java.util.ArrayList;

public class ItemProperties {

	private ArrayList<ItemFieldValue> fields;
	
	public ItemProperties() {
		fields = new ArrayList<ItemFieldValue>();
	}
	
	public String signature() {
		String ret = new String();
		for(ItemFieldValue f: fields) {
			ret+=f.value + " ";
		}
		return ret;
	}
	
	public void addValue(RegexField field,String value) {
		fields.add(new ItemFieldValue(field, value));
	}
	
	/*public ArrayList<String> missingFields() {
		ArrayList<String> ret = new ArrayList<String>();
		for(ItemField f:fields) {
			if(f.required && getValue(f.fieldName)==null) {
				ret.add(f.fieldName);
			}
		}
		return ret;
	}*/
	
	public ArrayList<ItemFieldValue> fields() {
		return fields;
	}
	
	/*public String getValue(String field) {
		return values.get(field);
	}*/
	
}
