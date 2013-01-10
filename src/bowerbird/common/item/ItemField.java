package bowerbird.common.item;

public class ItemField {

	public String fieldName;
	public boolean required;
	
	public ItemField(String fieldName) {
		this(fieldName,false);
	}
	
	public ItemField(String fieldName,boolean required) {
		this.fieldName = fieldName;
		this.required = required;
	}
	
}
