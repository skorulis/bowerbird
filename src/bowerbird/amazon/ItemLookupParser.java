package bowerbird.amazon;

import java.util.Map;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import bowerbird.common.PricePoint;

public class ItemLookupParser extends BaseParser{

	public static final String TAG_LowestNewPrice = "LowestNewPrice";
	public static final String TAG_LowestUsedPrice = "LowestUsedPrice";
	public static final String TAG_LowestRefurbishedPrice = "LowestRefurbishedPrice";
	public static final String TAG_TotalNew = "TotalNew";
	public static final String TAG_TotalUsed = "TotalUsed";
	public static final String TAG_TotalRefurbished = "TotalRefurbished";
	public static final String TAG_Amount = "Amount";
	
	
	public String currentTag;
	public PricePoint price;
	
	
	public ItemLookupParser(SignedRequestsHelper helper) {
		super(helper);
	}
	
	public void lookupItem(AmazonItem item) {
		Map<String, String> params = this.baseParameters();
        params.put("Operation", "ItemLookup");
        params.put("ItemId", item.asin);
        params.put("ResponseGroup","Large");
        
        price = new PricePoint();
        signAndParse(params);
        System.out.println(price);
	}
	
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
		//System.out.print(tabLevel + "<" + qName + ">");
		tabLevel+="  ";
		if(qName.equals(TAG_Amount)) {
			
		} else {
			currentTag = qName;
		}
		
	}
	
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
		tabLevel = tabLevel.substring(2);
		//System.out.print("</" + qName + ">");
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
		//System.out.println(new String(ch,start,length));
		if(currentTag.equals(TAG_TotalNew)) {
			price.newQuantity = Integer.parseInt(new String(ch,start,length));
		} else if(currentTag.equals(TAG_TotalUsed)) {
			price.usedQuantity = Integer.parseInt(new String(ch,start,length));
		} else if(currentTag.equals(TAG_TotalRefurbished)) {
			price.refurbQuantity = Integer.parseInt(new String(ch,start,length));
		} else if(currentTag.equals(TAG_LowestNewPrice)) {
			price.setNewPrice(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_LowestUsedPrice)) {
			price.setUsedPrice(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_LowestRefurbishedPrice)) {
			price.setRefurbPrice(Integer.parseInt(new String(ch,start,length)));
		} 
	}
	
	
}
