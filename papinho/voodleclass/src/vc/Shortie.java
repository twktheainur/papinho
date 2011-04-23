package vc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;



@SuppressWarnings("serial")
public class Shortie extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Hello, shorrtie!</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1>Welcome to shortie!</h1>");
		pw.println("<div id='login'>");
		pw.println("</div>");
		pw.println("<div id='post'>");
		pw.println("<form action='/post' method='GET'>");
		pw.println("<input type='text' name='user' value='Anon Ymous'/><br/>");
		pw.println("<textarea name='post' cols='100'>");
		pw.println("</textarea>");
		  pw.println("<br/>");
		pw.println("<input type='submit' name='submit' value='Post'/>");
		pw.println("</form>");
		pw.println("</div>");
		pw.println("<div id='messages'>");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Message");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			  String name = (String) result.getProperty("userName");
			  String message = (String) result.getProperty("message");
			  Date ts = (Date) result.getProperty("date");
			  pw.println("<div id='message'>\n<b>"+name+"</b>\n<br/>\n"+ts.toString()+"\n<p>"+message+"</p></div>\n");
			}
		pw.println("</div>");
		pw.println("</body>");
		pw.println("</html>");
	}
}
