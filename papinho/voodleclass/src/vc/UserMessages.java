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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
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
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String qarr[] = request.getPathInfo().split("/");
		String queryName="";
		if(qarr.length<2){
			response.sendRedirect("/");
		} else {
		   queryName= qarr[1];
		}
		
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE HTML>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Shortie!</title>");
		pw.println("<link rel='stylesheet' type='text/css' media='screen' href='/css/index.css'/>");
		pw.println("<link rel='icon' type='image/png'  href='/favicon.png' />");
		pw.println("<script src='scripts/index.js' type='text/javascript'></script>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1><a href='/'><img src='/images/logo.png' alt='Welcome to shortie'/></a></h1>");
		pw.println("<div id='login'>");
		Principal user = request.getUserPrincipal();
		if (user != null) {
			String name = user.getName();
			String login = String
			.format("I'm logged in as <strong>%s</strong> <a href='%s'>Sign out</a>",
					name, userService.createLogoutURL(thisURL));
			
			pw.println(login);
			pw.println("<a href='/subscriptions'>My Subscriptions</a>");
			Query q = new Query("Subscriptions");
			q.addFilter("userName", Query.FilterOperator.EQUAL, name);
			q.addFilter("to", Query.FilterOperator.EQUAL, queryName);
			PreparedQuery pq = datastore.prepare(q);
			if(pq.asList(FetchOptions.Builder.withLimit(1)).size()!=0){
				pw.println("<a href='/subscribe/"+queryName+"'>Subscribe!</a>");
			}
		} else {

			StringBuffer sb = new StringBuffer();
			sb.append("I'm an <strong>Anonymous</strong> user <a href='%s'>Sign in<img src='images/gl.jpg' alt='Google Accounts'/></a>");
			String htmlparsed = String.format(sb.toString(),
					userService.createLoginURL(thisURL));
			pw.println(htmlparsed);

		}
		pw.println("<a href='/users'>User list</a>");
		pw.println("</div>");
		pw.println("<div id='content'>");
		pw.println("<div id='messages'>");
		pw.println("<h2>"+queryName+"'s feed:</h2>");
		Query q = new Query("Message");
		q.addFilter("userName", Query.FilterOperator.EQUAL, queryName);
		q.addSort("date", SortDirection.DESCENDING);

		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable(FetchOptions.Builder.withLimit(30))) {
			String name = (String) result.getProperty("userName");
			String message = (String) result.getProperty("message");
			Date ts = (Date) result.getProperty("date");
			Boolean anon = (Boolean) result.getProperty("anon");

			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String tsFormatted = sd.format(ts);

			StringBuffer sb = new StringBuffer();
			sb.append("<div class='message'>");
			if (anon!=null && !anon) {
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
