package vc;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



@SuppressWarnings("serial")
public class Shortie extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		String thisURL = request.getRequestURI();
		UserService userService = UserServiceFactory.getUserService();
		
		PrintWriter pw = response.getWriter();
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
		
		if (request.getUserPrincipal() != null){		
			String login=String.format("I'm <input type='text' disabled name='user' value='%s'/>(<a href='%s'>sign out</a>)<br/>", request.getUserPrincipal().getName(),userService.createLogoutURL(thisURL));
			pw.println(login);
		}else {
			
			StringBuffer sb=new StringBuffer();
			sb.append("I'm <input type='text' name='user' value='Anon Ymous'/>(or <a href='%s'>sign in</a>)<br/>");
			String htmlparsed=String.format(sb.toString(),userService.createLoginURL(thisURL) );
			
			
			
			pw.println(htmlparsed);
			
			
		}
		
		
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
			  
			  SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			  String tsFormatted=sd.format(ts);
			  
			  StringBuffer sb=new StringBuffer();
			  sb.append("<div id='message'>");
			  sb.append("<b>%s</b> (on %s )<br/>");
			  sb.append("%s<br/>");
			  sb.append("</div>");
			 
			  String htmlparsed=String.format(sb.toString(),name,tsFormatted,message);
			  
			 
			  //pw.println("<div id='message'>\n<b>"+name+"</b>\n<br/>\n"+ts.toString()+"\n<p>"+message+"</p></div>\n");
			  
			  pw.println(htmlparsed);
			}
		pw.println("</div>");
		pw.println("</body>");
		pw.println("</html>");
	}
}
