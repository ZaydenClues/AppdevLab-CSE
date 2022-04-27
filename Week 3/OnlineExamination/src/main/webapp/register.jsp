<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">

<title>Register</title>
</head>
<body>
    <div class="login">
        <div class="form">
            <form action="UserRegister" name="myform" autocomplete="off" method="post">
            	<h1 align="center" class="header">Register User</h1>
                <p>Username</p>
                <input name="username" type="text" placeholder="Enter your username">
                <p>Password</p>
                <input name="password" type="password" placeholder="Enter your password">
                <p>Confirm Password</p>
                <input name="password" type="password" placeholder="Confirm your password">
                <button type="submit">Register</button>
                <div align="center">
                	<a href="login.jsp">Already have an account?</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>