package bowerbird.common;

import java.util.Date;

public class PricePoint {
	
	public Date date; 
	
	private int newQuantityMax;
	private int usedQuantityMax;
	private int refurbQuantityMax;
	
	private int usedPriceLow;
	private int newPriceLow;
	private int refurbPriceLow;
	
	private int usedPriceHigh;
	private int newPriceHigh;
	private int refurbPriceHigh;
	
	public PricePoint() {
		date = new Date();
		newQuantityMax = usedQuantityMax = refurbQuantityMax = 0;
		usedPriceLow = newPriceLow = refurbPriceLow = -1;
		usedPriceHigh = newPriceHigh = refurbPriceHigh = -1;
	}
	
	public String toString() {
		String s = new String();
		s+="New:" + newQuantityMax + "@" + newPriceLow + " - " + newPriceHigh + "\n";
		s+="Used:" + usedQuantityMax + "@" + usedPriceLow + " - " + usedPriceHigh+ "\n";
		s+="Refurb:" + refurbQuantityMax + "@" + refurbPriceLow + " - " + refurbPriceHigh + "\n";
		return s;
	}
	
	public void setNewQuantity(int amount) {
		newQuantityMax = Math.max(newQuantityMax, amount);
	}
	
	public void setUsedQuantity(int amount) {
		usedQuantityMax = Math.max(usedQuantityMax, amount);
	}
	
	public void setRefurbQuantity(int amount) {
		refurbQuantityMax = Math.max(refurbQuantityMax, amount);
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
