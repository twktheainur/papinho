package vc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


/**
 * Servlet is responsible for adding the new post to the pool and give the user feedback about the action.
 * @author jander/andon
 */
public class Unsubscribe extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Persist information (post the message)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Principal user = request.getUserPrincipal();
			String qarr[] = request.getPathInfo().split("/");
			String queryName="";
			response.setContentType("text/plain");
			if(qarr.length<2){
				response.getWriter().println("Redirect short");
				response.sendRedirect("/");
			} else {
			   queryName= qarr[1];
			}
			if (user != null && !queryName.equals(user.getName())){
				String name=user.getName();
				Query q = new Query("Subscription").setKeysOnly();
				response.getWriter().println(queryName);
				q.addFilter("to", Query.FilterOperator.EQUAL, queryName);
				q.addFilter("userName", Query.FilterOperator.EQUAL, name);
				PreparedQuery pq = datastore.prepare(q);
				for(Entity e: pq.asIterable()){
					datastore.delete(e.getKey());
				}
			}
			response.sendRedirect("/");
	}
}
