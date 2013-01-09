package bowerbird.common;

import bowerbird.amazon.AmazonItem;
import bowerbird.common.item.ItemProperties;

public class Commodity {

	private AmazonItem amazonItem;
	private String name;
	private ItemProperties properites;
	
	public Commodity() { }
	
	//Creates a commodity using an amazon item to initialise it
	public Commodity(AmazonItem amazonItem) {
		this.setAmazonItem(amazonItem);
		this.name = amazonItem.title;
	}
	
	public ItemProperties properties() {
		return properites;
	}
	
	public String name() {
		return name;
	}
	
	public AmazonItem amazonItem() {
		return amazonItem;
	}
	
	public void setAmazonItem(AmazonItem item) {
		this.amazonItem = item;
	}
	
	public String signature() {
		return properites.signature();
	}
	
}
