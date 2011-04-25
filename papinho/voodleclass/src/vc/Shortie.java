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
public class Shortie extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		String thisURL = request.getRequestURI();
		UserService userService = UserServiceFactory.getUserService();

		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE HTML>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Hello, shorrtie!</title>");
		pw.println("<link rel='stylesheet' type='text/css' media='screen' href='css/index.css'/>");
		pw.println("<script src='scripts/index.js' type='text/javascript'></script>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1><!--<img src='images/logo.png' alt='Welcome to shortie'/>-->.</h1>");
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
		pw.println("<div id='post'>");
		pw.println("<h2>What's on your mind?:</h2>");
		pw.println("<form action='/post' method='GET'>");
		String css = "";
		if (request.getAttribute("err") != null
				&& request.getAttribute("err").equals("post")) {
			css = "border-color:red;";
		} else {
			css = "";
		}

		if (user == null) {
			pw.println("<span id='anonname'>Post as: <input name='user' type='text' value='Anonymous' onfocus='set_bg(\"#f6F5F3\",this.id)' onblur='set_bg(\"#ffffff\",this.id)' id='uname'/></span>");
		} else {
			pw.println("<input name='user' type='hidden' value='Anonymous'/>");
		}
		pw.println("<textarea name='post' style='"
				+ css
				+ "'  cols='100' id='textarea' onfocus='set_bg(\"#f6F5F3\",this.id)' onblur='set_bg(\"#ffffff\",this.id)'>");
		pw.println("</textarea>");
		pw.println("<br/>");
		pw.println("<input type='submit' name='submit' value='Share it!' id='submit'/>");
		pw.println("</form>");
		pw.println("</div>");
		pw.println("<div id='messages'>");
		pw.println("<h2>My feed:</h2>");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("Message");
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
				sb.append("<strong><a href='/user/"+user+"'>%s</a></strong> (on %s )<br/>\n");
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
