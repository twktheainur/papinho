package vc;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet for the user interface, prints out the HTML
 * 
 * @author jander
 */
@SuppressWarnings("serial")
public class UserMessages extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		String thisURL = request.getRequestURI();
		UserService userService = UserServiceFactory.getUserService();
		
		String qarr[] = request.getPathInfo().split("/");
		if(qarr.length<2){
			response.sendRedirect("/");
		}
		String queryName = qarr[1];
		
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE HTML>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title></title>");
		pw.println("<link rel='stylesheet' type='text/css' media='screen' href='/css/index.css'/>");
		pw.println("<script src='scripts/index.js' type='text/javascript'></script>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1></h1>");
		pw.println("<div id='login'>");
		Principal user = request.getUserPrincipal();
		if (user != null) {
			String login = String
					.format("I'm logged in as <strong>%s</strong>(<a href='%s'>sign out</a>)<br/>",
							request.getUserPrincipal().getName(),
							userService.createLogoutURL(thisURL));
			pw.println(login);
		} else {

			StringBuffer sb = new StringBuffer();
			sb.append("I'm an <strong>Anonymous</strong> user(<a href='%s'>sign in</a> with <img src='images/gl.jpg' alt='Google Accounts'/>)<br/>");
			String htmlparsed = String.format(sb.toString(),
					userService.createLoginURL(thisURL));
			pw.println(htmlparsed);

		}
		pw.println("</div>");
		pw.println("<div id='content'>");
		pw.println("<div id='messages'>");
		pw.println("<h2>"+queryName+"'s feed:</h2>");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("Message");
		q.addFilter("userName", Query.FilterOperator.EQUAL, queryName);
		q.addSort("date", SortDirection.DESCENDING);

		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			String name = (String) result.getProperty("userName");
			String message = (String) result.getProperty("message");
			Date ts = (Date) result.getProperty("date");
			Boolean anon = (Boolean) result.getProperty("anon");

			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String tsFormatted = sd.format(ts);

			StringBuffer sb = new StringBuffer();
			sb.append("<div class='message'>");
			if (!anon) {
				sb.append("<strong><a href='/user/"+name+"'>%s</a></strong> (on %s )<br/>\n");
			} else {
				sb.append("<strong>%s</strong> (on %s )<br/>\n");
			}
			sb.append("%s<br/>");
			sb.append("</div>");

			String htmlparsed = String.format(sb.toString(), name, tsFormatted,
					message);

			pw.println(htmlparsed);
		}
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</body>");
		pw.println("</html>");
	}
}
