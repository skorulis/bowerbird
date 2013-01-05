package bowerbird.amazon;

import java.util.ArrayList;

import bowerbird.common.PricePoint;

public class AmazonItem {

	public String title;
	public String asin;
	public String allOffers;
	
	public ArrayList<PricePoint> prices;
	
	public AmazonItem() {
		prices = new ArrayList<PricePoint>();
	}
	
	public String toString() {
		String ret = title + " = " + asin+"\n";
		ret+=allOffers + "\n";
		if(prices.size()>0) {
			ret+=prices.get(prices.size()-1).toString();
		}
		return ret;
	}
	
	public void addPrice(PricePoint pi) {
		prices.add(pi);
	}

}
