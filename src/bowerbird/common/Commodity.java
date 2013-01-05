package bowerbird.common;

import bowerbird.amazon.AmazonItem;

public class Commodity {

	private AmazonItem amazonItem;
	private String name;
	
	public Commodity() { }
	
	//Creates a commodity using an amazon item to initialise it
	public Commodity(AmazonItem amazonItem) {
		this.setAmazonItem(amazonItem);
		this.name = amazonItem.title;
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
	
}
