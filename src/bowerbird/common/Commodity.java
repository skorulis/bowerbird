package bowerbird.common;

import bowerbird.amazon.AmazonItem;

public class Commodity {

	private AmazonItem amazonItem;
	
	public Commodity() {
		
	}
	
	public AmazonItem amazonItem() {
		return amazonItem;
	}
	
	public void setAmazonItem(AmazonItem item) {
		this.amazonItem = item;
	}
	
}
