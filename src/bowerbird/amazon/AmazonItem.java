package bowerbird.amazon;

import java.util.ArrayList;

import bowerbird.common.PricePoint;

public class AmazonItem {

	public String title;
	public String asin;
	
	public ArrayList<PricePoint> prices;
	
	public AmazonItem() {
		prices = new ArrayList<PricePoint>();
	}
	
	public String toString() {
		return title + " = " + asin;
	}
	
	public void addPrice(PricePoint pi) {
		prices.add(pi);
	}

}
