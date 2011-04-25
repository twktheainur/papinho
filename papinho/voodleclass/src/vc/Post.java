package vc;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


/**
 * Servlet is responsible for adding the new post to the pool and give the user feedback about the action.
 * @author jander/andon
 */
public class Post extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Persist information (post the message)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
			String name = request.getParameter("user");
			String text = request.getParameter("post");
			
			if(text==null||text.trim().length()==0){
				//response.sendRedirect("/vclass2");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/vclass2");
				request.setAttribute("err", "post");
				dispatcher.forward(request, response);
				return;
			}
			
			if (request.getUserPrincipal() != null){
				name=request.getUserPrincipal().getName();
			}
			
			if(name==null){
				name = "Anonymous";
			}
			
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity message = new Entity("Message");
			message.setProperty("userName", name);
			message.setProperty("message", text);
			message.setProperty("date",Calendar.getInstance().getTime());
			datastore.put(message);
			request.removeAttribute("err");
			
			response.sendRedirect("/vclass2");
			
			
	}

}
