package bowerbird.amazon;

import java.util.ArrayList;

import bowerbird.common.parser.ItemParser;
import bowerbird.common.parser.ItemParserManager;
import bowerbird.common.parser.ParseResult;


public class AmazonSearchManager {
	
	private ItemSearchParser searchParser;
	private ItemLookupParser lookupParser;
	private SignedRequestsHelper helper;
	private static ItemParser parser;
	
	public AmazonSearchManager(ItemParserManager ipm) {
		try {
            helper = SignedRequestsHelper.getInstance(Constants.ENDPOINT, Constants.AWS_KEY, Constants.AWS_SECRET);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        searchParser = new ItemSearchParser(helper);
        lookupParser = new ItemLookupParser(helper);
        parser = new ItemParser(ipm);
	}
	
	public ArrayList<ParseResult> performSearch(String query) {
		ArrayList<AmazonItem> results = searchParser.performSearch("Electronics", query);
		ArrayList<ParseResult> parsed = new ArrayList<ParseResult>();
		for (AmazonItem item : results) {
        	ParseResult res = parser.parseTitle(item.title);
        	parsed.add(res);
        	System.out.println(res);
		}
		return parsed;
	}
	
}
