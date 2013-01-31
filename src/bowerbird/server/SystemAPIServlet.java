package bowerbird.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bowerbird.system.SystemDetails;

import com.google.gson.Gson;

public class SystemAPIServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
		
        Gson gson = new Gson();
        SystemDetails system = new SystemDetails();
        
        response.getWriter().print(gson.toJson(system));
        response.setStatus(200);
    }
	
}
