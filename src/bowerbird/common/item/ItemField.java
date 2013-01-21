package bowerbird.common.item;

import com.google.gson.annotations.Expose;

public class ItemField {

	@Expose public String fieldName;
	@Expose public boolean required;
	
	public ItemField(String fieldName) {
		this(fieldName,false);
	}
	
	public ItemField(String fieldName,boolean required) {
		this.fieldName = fieldName;
		this.required = required;
	}
	
}
