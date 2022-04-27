<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>Login</title>
</head>
<body>
    <div class="login">
        <div class="form">
            <form action="UserLogin" name="myform" autocomplete="off" method="post">
            	<h1 align="center" class="header">USER LOGIN</h1>
                <p>Username</p>
                <input name="username" type="text" placeholder="Enter your username">
                <p>Password</p>
                <input name="password" type="password" placeholder="Enter your password">
                <button type="submit">Login</button>
                <div align="center">
                	<a href="register.jsp">New User?</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>