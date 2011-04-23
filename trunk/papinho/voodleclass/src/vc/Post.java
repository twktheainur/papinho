package vc;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


public class Post extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	public Post() {
		super();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			String name = req.getParameter("name");
			String text = req.getParameter("post");
			if(name==null){
				name = "Anonymous";
			}
			if(text==null){
				throw new IOException("Type a message buddy!");
			}
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><meta http-equiv='refresh' content='0;URL=/'></head></html>");
			
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}
}
