package vc;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
public class UserList extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		String thisURL = request.getRequestURI();
		UserService userService = UserServiceFactory.getUserService();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

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
		List<String> subs = null;
		if (user != null) {
			String name = user.getName();
			String login = String
					.format("I'm logged in as <strong>%s</strong> <a href='%s'>Sign out</a>",
							name, userService.createLogoutURL(thisURL));
			pw.println(login);
			pw.println("<a href='/user/" + user.getName() + "'>My Profile</a>");
			pw.println("<a href='/subscriptions'>My Subscriptions</a>");
			subs = new ArrayList<String>();
			Query q = new Query("Subscription");
			q.addFilter("userName", Query.FilterOperator.EQUAL, name);
			PreparedQuery pq = datastore.prepare(q);
			for (Entity e : pq.asIterable()) {
				subs.add((String) e.getProperty("to"));
			}
		} else {

			StringBuffer sb = new StringBuffer();
			sb.append("I'm an <strong>Anonymous</strong> user <a href='%s'>Sign in<img src='/images/gl.jpg' alt='Google Accounts'/></a>");
			String htmlparsed = String.format(sb.toString(),
					userService.createLoginURL(thisURL));
			pw.println(htmlparsed);
		}
		pw.println("</div>");
		pw.println("<div id='content'>");
		pw.println("<div id='messages'>");
		pw.println("<h2>Users:</h2>");
		Query q = new Query("User");
		q.addSort("userName", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable(FetchOptions.Builder.withLimit(30))) {
			String name = (String) result.getProperty("userName");
			pw.println("<div class='message'>");
			pw.println("<strong><a href='/user/" + name + "'>" + name
					+ "</a>\n");
			if (user!=null && !name.equals(user.getName())) {
				if (user != null && !subs.contains(name)) {
					pw.println("<a href='/subscribe/" + name
							+ "'>Subscribe!</a>");
				} else {
					pw.println("<a href='/unsubscribe/" + name
							+ "'>Unsubscribe!</a>");
				}
			}
			pw.println("</div>");
		}
		pw.println("</div>");
		pw.println("</div>");
		pw.println("</body>");
		pw.println("</html>");
	}
}
