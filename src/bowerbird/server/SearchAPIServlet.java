package bowerbird.server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bowerbird.amazon.AmazonSearchManager;
import bowerbird.common.parser.ItemParserManager;
import bowerbird.common.parser.ParseResult;

public class SearchAPIServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1155071927291593852L;
	private static final String QUERY = "query";
	private Gson gson;
	
	private AmazonSearchManager searchManager;
	
	public SearchAPIServlet(ItemParserManager ipm) {
		searchManager = new AmazonSearchManager(ipm);
		gson = new Gson();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		String query = request.getParameter(QUERY);
		
		response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        if(query==null) {
        	ServerError error = ServerError.missingParam("query");
        	response.getWriter().print(gson.toJson(error));
        	response.setStatus(501);
        	return;
        }
        
        ArrayList<ParseResult> results = searchManager.performSearch(query);
        APIResult res = new APIResult(results);
        response.getWriter().print(gson.toJson(res));
    	response.setStatus(200);
		
    }
	
	public AmazonSearchManager searchManager() {
		return searchManager;
	}

}
