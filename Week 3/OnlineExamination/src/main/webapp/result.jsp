<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "jakarta.servlet.http.*,jakarta.servlet.*" %>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
function eval(){
	let answers = JSON.parse(localStorage.getItem("answers"));
	for(let i = 0; i < 8; i++){
		console.log(answers[i]);
	}
}
</script>
<title>Insert title here</title>
</head>
<body>

<%
String rollno = request.getParameter("rollno");
String name = request.getParameter("name");
String str1=request.getParameter("ans1");
String str2=request.getParameter("ans2");
String str3=request.getParameter("ans3");

String resp = request.getParameter("ans");

resp = resp.substring(1,resp.length()-1);

String[] respArray = resp.split(",");
int mark=0;
String url = "jdbc:postgresql://localhost:5432/exam";
String user = "saran";
String password = "123456";
Class.forName("org.postgresql.Driver");
Connection con=DriverManager.getConnection(url,user,password);
Statement stmt=con.createStatement();
ResultSet rs=stmt.executeQuery("SELECT * FROM answers");

int sz = respArray.length;

while(rs.next() && sz != 0){
	String s = '"' + rs.getString("answer") + '"';
	if(respArray[rs.getInt("qn_no")-1].equals(s)) {
		mark++;
	}
	sz--;
}

PreparedStatement stmt12=con.prepareStatement("select * from marklist where rollno=?");
stmt12.setString(1,rollno);
ResultSet rs1 = stmt12.executeQuery();

if(rs1.isBeforeFirst()) {
	PreparedStatement stmt1=con.prepareStatement("update marklist set marks=? where rollno=?");
	stmt1.setInt(1,mark);
	stmt1.setString(2,rollno);
	try{
		stmt1.executeQuery();
	}catch(Exception e){
	out.println("<h4>Your Mark Is Updated to : "+mark+"</h4>");
	}
} else{

PreparedStatement stmt2=con.prepareStatement("insert into marklist values(?,?,?)");
stmt2.setString(1,rollno);
stmt2.setString(2,name);
stmt2.setInt(3, mark);
try{
	ResultSet rs2 = stmt2.executeQuery();
}catch(Exception e){

 out.println("<h4>Your Mark Is : "+mark+"</h4>");
}
}

%>

</body>
</html>