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
	public static final String TAG_Description = "Description";
	public static final String TAG_URL = "URL";
	
	public static final String DESC_AllOffers = "All Offers";
	
	//Parsing variables
	private String currentTag;
	private String descriptionText;
	
	
	private PricePoint price;
	private AmazonItem item;
	
	public ItemLookupParser(SignedRequestsHelper helper) {
		super(helper);
		descriptionText="";
	}
	
	public void lookupItem(AmazonItem item) {
		this.item = item;
		Map<String, String> params = this.baseParameters();
        params.put("Operation", "ItemLookup");
        params.put("ItemId", item.asin);
        params.put("ResponseGroup","Large");
        
        price = new PricePoint();
        signAndParse(params);
        item.addPrice(price);
	}
	
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
		if(qName.equals(TAG_Amount)) {
			
		} else {
			currentTag = qName;
		}
		
	}
	
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
		if(currentTag.equals(TAG_TotalNew)) {
			price.setNewQuantity(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_TotalUsed)) {
			price.setUsedQuantity(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_TotalRefurbished)) {
			price.setRefurbQuantity(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_LowestNewPrice)) {
			price.setNewPrice(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_LowestUsedPrice)) {
			price.setUsedPrice(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_LowestRefurbishedPrice)) {
			price.setRefurbPrice(Integer.parseInt(new String(ch,start,length)));
		} else if(currentTag.equals(TAG_Description)) {
			descriptionText = new String(ch,start,length);
		} else if(currentTag.equals(TAG_URL)) {
			if(descriptionText.equals(DESC_AllOffers)) {
				item.allOffers = new String(ch,start,length);
				descriptionText = "";
			}
		}
			
	}
	
	
}
