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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


/**
 * Servlet is responsible for adding the new post to the pool and give the user feedback about the action.
 * @author jander/andon
 */
public class Subscribe extends HttpServlet {

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
			if (user != null){
				String name=user.getName();
				Query q = new Query("User");
				response.getWriter().println(queryName);
				q.addFilter("userName", Query.FilterOperator.EQUAL, queryName);
				PreparedQuery pq = datastore.prepare(q);
				if(pq.asList(FetchOptions.Builder.withLimit(1)).size()!=0){
					Entity sub = new Entity("Subscription",name+"/"+queryName);
					sub.setProperty("userName", name);
					sub.setProperty("to", queryName);
					datastore.put(sub);
				}
			}
			response.sendRedirect("/");
	}
}
