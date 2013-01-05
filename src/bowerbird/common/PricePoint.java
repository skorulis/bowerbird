package bowerbird.common;

import java.util.Date;

public class PricePoint {
	
	public Date date; 
	
	public int newQuantity;
	public int usedQuantity;
	public int refurbQuantity;
	
	private int usedPriceLow;
	private int newPriceLow;
	private int refurbPriceLow;
	
	private int usedPriceHigh;
	private int newPriceHigh;
	private int refurbPriceHigh;
	
	public PricePoint() {
		date = new Date();
		newQuantity = usedQuantity = refurbQuantity = 0;
		usedPriceLow = newPriceLow = refurbPriceLow = -1;
		usedPriceHigh = newPriceHigh = refurbPriceHigh = -1;
	}
	
	public String toString() {
		String s = new String();
		s+="New:" + newQuantity + "@" + newPriceLow + " - " + newPriceHigh + "\n";
		s+="Used:" + usedQuantity + "@" + usedPriceLow + " - " + usedPriceHigh+ "\n";
		s+="Refurb:" + refurbQuantity + "@" + refurbPriceLow + " - " + refurbPriceHigh + "\n";
		return s;
	}
	
	public void setNewPrice(int price) {
		newPriceLow = newPriceLow==-1?price:Math.min(price, newPriceLow);
		newPriceHigh = newPriceHigh==-1?price:Math.min(price, newPriceHigh);
	}
	
	public void setUsedPrice(int price) {
		usedPriceLow = usedPriceLow==-1?price:Math.min(price, usedPriceLow);
		usedPriceHigh = usedPriceHigh==-1?price:Math.min(price, usedPriceHigh);
	}
	
	public void setRefurbPrice(int price) {
		refurbPriceLow = refurbPriceLow==-1?price:Math.min(price, refurbPriceLow);
		refurbPriceHigh = refurbPriceHigh==-1?price:Math.min(price, refurbPriceHigh);
	}
	
}
