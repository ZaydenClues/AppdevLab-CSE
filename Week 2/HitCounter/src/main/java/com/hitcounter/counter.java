package com.hitcounter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class counter
 */
public class counter extends HttpServlet { 
	private static final long serialVersionUID = 1L;
	   private int counter;  


	   public void init() { 
		   System.out.println("New session started, resetting hit counter to 0...");
	      counter = 0; 
	   }  


	   public void doGet(HttpServletRequest request, HttpServletResponse response) 

	      throws ServletException, IOException { 

	      response.setContentType("text/html"); 

	      counter++;  

	      PrintWriter out = response.getWriter(); 

	      String title = "Total Number of Hits"; 

	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n"; 


	      out.println(docType + 

	         "<html>\n" + 

	            "<head><title>" + title + "</title></head>\n" + 

	            "<body bgcolor = \"#f0f0f0\">\n" + 

	               "<h1 align = \"center\">" + title + "</h1>\n" + 

	               "<h2 align = \"center\">" + counter + "</h2>\n" + 

	            "</body> "+

	         "</html>"); 
	   } 

	   public void destroy() {  
	   }  

	}
