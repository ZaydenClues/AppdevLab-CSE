package com.exam;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String url = "jdbc:postgresql://localhost:5432/exam";
	private String user = "saran";
	private String password = "123456";

	Connection conn = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url,user,password);
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			response.setContentType("text/html");
			
			PrintWriter out = response.getWriter();
			
			PreparedStatement insertUser = conn.prepareStatement("insert into users values(?,?)");
			insertUser.setString(1,username);
			insertUser.setString(2,password);
			
			PreparedStatement checkUser = conn.prepareStatement("select * from users where username=?");
			checkUser.setString(1,username);
			ResultSet rs = checkUser.executeQuery();
			
			if(rs.isBeforeFirst()) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('User already exists. Please create a new account to continue.');");
				out.println("location='/OnlineExamination/register.jsp';");
				out.println("</script>");
			}else {
				insertUser.execute();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Thank you for registering. Please login to write your exam.');");
				out.println("location='/OnlineExamination/login.jsp';");
				out.println("</script>");
		}
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

