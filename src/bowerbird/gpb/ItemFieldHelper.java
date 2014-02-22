package bowerbird.gpb;

import bowerbird.gpb.BowerbirdMessageProtocolGPB.ItemField;

public class ItemFieldHelper {

	public static ItemField makeField(String name) {
		return makeField(name, false);
	}
	
	public static ItemField makeField(String name,boolean required) {
		return makeField("--", name, required);
	}
	
	public static ItemField makeField(String ident,String name,boolean required) {
		return ItemField.newBuilder().setIdent(ident).setFieldName(name).setRequired(required).build();
	}
	
}
