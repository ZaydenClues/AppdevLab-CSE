package com.course.registration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.*;


public class CourseReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String url = "jdbc:postgresql://localhost:5432/coursereg";
	private String user = "saran";
	private String password = "lab";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserLogin.hitCount++;
		System.out.println("Hitcounter = " + UserLogin.hitCount);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserLogin.hitCount++;
		System.out.println("Hitcounter = " + UserLogin.hitCount);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url,user,password);
			String username = request.getParameter("username");
			int rollno = Integer.parseInt(request.getParameter("rollno"));
			int year = Integer.parseInt(request.getParameter("year"));
			int semester = Integer.parseInt(request.getParameter("sem"));
			String course = request.getParameter("course");
			
			PreparedStatement insertCourse = conn.prepareStatement("insert into registrations values(?,?,?,?,?)");
			insertCourse.setString(1, username);
			insertCourse.setInt(2, rollno);
			insertCourse.setInt(3, year);
			insertCourse.setInt(4, semester);
			insertCourse.setString(5, course);
			
			int flag = 1;
			
			PreparedStatement checkCourse = conn.prepareStatement("select * from registrations where username=?");
			checkCourse.setString(1,username);
			ResultSet rs = checkCourse.executeQuery();
				while (rs.next()){
						  int xsem = rs.getInt("semester");
						  int xyear = rs.getInt("year");
				          String xcourse = rs.getString("course");
				          if(xcourse.equals(course) && xsem == semester && xyear == year) {
				        	  	flag = 0;
				        	  	out.println("<script type=\"text/javascript\">");
								out.println("alert('Course already registered!');");
								out.println("location='/CourseRegistration/login.html';");
								out.println("</script>");
				          }
			}
				if(flag != 0) {
					insertCourse.executeQuery();
				}
					
			
		}catch(Exception e) {
		}
		String username = request.getParameter("username");
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Course Registered " + username + "!');");
		out.println("location='/CourseRegistration/login.html';");
		out.println("</script>");
		
	}

}
