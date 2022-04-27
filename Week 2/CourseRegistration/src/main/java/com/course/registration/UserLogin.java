package com.course.registration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLogin extends HttpServlet {
	public static int hitCount;
	   public void init() { 
		  System.out.println("New session started, resetting hit counter to 0...");
	      hitCount = 0;
	   } 
	   
	private static final long serialVersionUID = 1L;
	
	private String url = "jdbc:postgresql://localhost:5432/coursereg";
	private String user = "saran";
	private String password = "lab";
	
	Connection conn = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		hitCount++;
		System.out.println("Hitcounter = " + hitCount);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		hitCount++;
		System.out.println("Hitcounter = " + hitCount);
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url,user,password);
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			response.setContentType("text/html");
			
			PrintWriter out = response.getWriter();
			
			PreparedStatement checkCred = null;
			String sql = "select password from users where username=?";
			checkCred = conn.prepareStatement(sql);
			checkCred.setString(1, username);
			ResultSet rs = checkCred.executeQuery();
			if(!rs.isBeforeFirst()) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('User does not exist. Please create a new account to continue.');");
				out.println("location='/CourseRegistration/login.html';");
				out.println("</script>");
			}
			while(rs.next()) {
				String truePassword = rs.getString("password");
				if(truePassword.equals(password)) {
					HttpSession session = request.getSession();
					session.setAttribute("user", username);
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Successfully logged in.');");
					out.println("</script>");
					out.println("<link rel=\"stylesheet\" href=\"/CourseRegistration/style/style.css\">");
					out.println("<div class=\"login\">");
					out.println("<div class=\"form\">");
					out.println("<form name=\"myform\" autocomplete=\"off\" method=\"post\" action=\"CourseReg\">");
					out.println("<h1 align=\"center\">Course Registration</h1>");
					out.println("<h2 align=\"center\">Welcome " + session.getAttribute("user") + "</h2>");
					out.println("<p>Username</p>");
					out.println("<input name=\"username\" type=\"text\" placeholder=\"Enter your username\">");
					out.println("<p>Roll No</p>");
					out.println("<input name=\"rollno\" type=\"text\" placeholder=\"Enter your roll no\">");
					out.println("<p>Year</p>");
					out.println("<input name=\"year\" type=\"text\" placeholder=\"Enter year\">");
					out.println("<p>Semester</p>");
					out.println("<input name=\"sem\" type=\"text\" placeholder=\"Enter semester\">");
					out.println("<p>Course</p>");
					out.println("<input name=\"course\" type=\"text\" placeholder=\"Enter course\">");
					out.println("<button type=\"submit\">Submit</button>");
					out.println("</form>");
					out.println("</div>");
					out.println("</div>");
				}else {
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Incorrect Password.');");
					out.println("location='/CourseRegistration/login.html';");
					out.println("</script>");
				}
			}
			
				
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void destroy() { 
	   } 

}
