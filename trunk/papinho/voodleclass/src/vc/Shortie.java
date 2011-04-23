package vc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

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
		pw.println("<input type='text' name='user' value='Anon Ymous'/>");
		pw.println("<textarea name='post' cols='100'>");
		pw.println("</textarea>");
		  pw.println("<br/>");
		pw.println("<input type='submit' name='submit' value='Post'/>");
		pw.println("</form>");
		pw.println("</div>");
		pw.println("<div id='messages'>");
		pw.println("</div>");
		pw.println("</body>");
		pw.println("</html>");
	}
}
