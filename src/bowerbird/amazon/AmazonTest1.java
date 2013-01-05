package bowerbird.amazon;

import java.util.ArrayList;

public class AmazonTest1 {

	private static ItemSearchParser searchParser;
	private static ItemLookupParser lookupParser;
	private static SignedRequestsHelper helper;

	public static void main(String[] args) throws InterruptedException {
        try {
            helper = SignedRequestsHelper.getInstance(Constants.ENDPOINT, Constants.AWS_KEY, Constants.AWS_SECRET);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        searchParser = new ItemSearchParser(helper);
        lookupParser = new ItemLookupParser(helper);
		
        
        ArrayList<AmazonItem> results = searchParser.performSearch("Electronics", "iPhone");
        for (AmazonItem searchResult : results) {
			System.out.println(searchResult);
			Thread.sleep(200);
			lookupParser.lookupItem(searchResult);
		}
        System.out.println("Lookup finished");
	}
	

}
