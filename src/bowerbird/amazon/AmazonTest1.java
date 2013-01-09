package bowerbird.amazon;

import java.util.ArrayList;

import com.google.gson.Gson;

import bowerbird.common.Commodity;
import bowerbird.common.item.ItemProperties;
import bowerbird.common.parser.ItemParser;

import redis.clients.jedis.Jedis;

public class AmazonTest1 {

	private static ItemSearchParser searchParser;
	private static ItemLookupParser lookupParser;
	private static SignedRequestsHelper helper;
	private static Jedis jedis;
	private static Gson gson;
	private static ItemParser parser;

	public static void main(String[] args) throws InterruptedException {
		jedis = new Jedis("localhost");
		gson = new Gson();
        try {
            helper = SignedRequestsHelper.getInstance(Constants.ENDPOINT, Constants.AWS_KEY, Constants.AWS_SECRET);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        searchParser = new ItemSearchParser(helper);
        lookupParser = new ItemLookupParser(helper);
        parser = new ItemParser();
		
		
        ArrayList<AmazonItem> results = searchParser.performSearch("Electronics", "iPhone");
        Commodity commodity;
        for (AmazonItem item : results) {
        	String comJson = jedis.get(item.title);
        	if(comJson==null) {
        		commodity = new Commodity(item);
        		System.out.println("Created commodity " + commodity.name());
        	} else {
        		commodity = gson.fromJson(comJson, Commodity.class);
        		//commodity.generateProperties();
        		//System.out.println(commodity.signature());
        		ItemProperties ip = parser.parseTitle(commodity.name());
        		System.out.println("Got properties " + ip.signature() + " for " + commodity.name());
        		//System.out.println("Found commodity " + commodity.name());
        	}
			//lookupParser.lookupItem(commodity.amazonItem());
			//System.out.println(commodity.amazonItem());
			jedis.set(commodity.name(),gson.toJson(commodity));
			Thread.sleep(200);
		}
        System.out.println("Lookup finished");
	}
	

}
