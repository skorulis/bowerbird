package bowerbird.amazon;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import bowerbird.common.parser.ItemParserManager;
import bowerbird.persistence.KVStore;
import bowerbird.server.SearchAPIServlet;
import bowerbird.server.SystemAPIServlet;


public class AmazonTest1 {

	
	private static ItemParserManager parserManager;
	private static KVStore kvStore;
	private static SearchAPIServlet searchAPI;
	private static SystemAPIServlet systemAPI;
	
	public static void main(String[] args) throws Exception {
		kvStore = new KVStore();
		parserManager = new ItemParserManager(kvStore);
		int randPort = 1024 + (int) (Math.random()*2048);
		Server server = new Server(randPort);
		
		searchAPI = new SearchAPIServlet(parserManager);
		systemAPI = new SystemAPIServlet();
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(searchAPI),"/search.json");
        context.addServlet(new ServletHolder(systemAPI), "/system.json");
		
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("../../html/bowerbird/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {context, resource_handler});
        server.setHandler(handlers);
        
        searchAPI.searchManager().performSearch("iPhone");
        
        server.start();
        server.join();
        
        
        /*ArrayList<AmazonItem> results = searchParser.performSearch("Electronics", "iPhone");
        Commodity commodity;
        for (AmazonItem item : results) {
        	commodity = new Commodity(item);
        	ParseResult res = parser.parseTitle(commodity.name());
        	System.out.println(res);
        	
			//Thread.sleep(200);
		}*/
        System.out.println("Lookup finished");
	}
	

}
