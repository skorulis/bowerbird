package bowerbird.common.item;

import bowerbird.gpb.BowerbirdMessageProtocolGPB.RegexField;

public class ItemFieldValue {

	public String fieldId;
	public String value;
	
	public ItemFieldValue(RegexField field,String value) {
		this.fieldId = field.getIdent();
		this.value = value;
	}
	
}
