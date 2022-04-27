package com.exam;

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

/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	private String url = "jdbc:postgresql://localhost:5432/exam";
	private String user = "saran";
	private String password = "123456";
	
	Connection conn = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				out.println("location='/OnlineExamination/login.jsp';");
				out.println("</script>");
			}
			while(rs.next()) {
				String truePassword = rs.getString("password");
				if(truePassword.equals(password)) {
					
				}else {
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Incorrect Password.');");
					out.println("location='/OnlineExamination/login.jsp';");
					out.println("</script>");
				}
			}
			
				
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
