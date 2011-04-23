package vc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
			
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><meta http-equiv='refresh' content='0;URL=/'></head></html>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}
}
