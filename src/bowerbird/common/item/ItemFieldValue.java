package bowerbird.common.item;

public class ItemFieldValue {

	public String fieldId;
	public String value;
	
	public ItemFieldValue(RegexField field,String value) {
		this.fieldId = field.id();
		this.value = value;
	}
	
}
