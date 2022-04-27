<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "jakarta.servlet.http.*,jakarta.servlet.*" %>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/examstyle.css">
<title>Insert title here</title>
</head>
<body>

<script>
	let questions = [
		['Question 1','Option a','Option b', 'Option c', 'Option d'],
		['Question 2','Option a','Option b', 'Option c', 'Option d'],
		['Question 3','Option a','Option b', 'Option c', 'Option d'],
		['Question 4','Option a','Option b', 'Option c', 'Option d'],
		['Question 5','Option a','Option b', 'Option c', 'Option d'],
		['Question 6','Option a','Option b', 'Option c', 'Option d'],
		['Question 7','Option a','Option b', 'Option c', 'Option d'],
		['Question 8','Option a','Option b', 'Option c', 'Option d'],
	]
	
	let answers = [];
	
	function saveAnswer(element,question){
		console.log("save");
		let message = document.getElementById("box-2");
		let isChecked = document.querySelector('input[name="ans"]:checked');
		if(isChecked != null){
			answers[question - 1] = isChecked.value;
			message.innerHTML += '<div id="message">' + isChecked.value + ' is saved</div>';
		} else{
			message.innerHTML += '<div id="message">Nothing is selected</div>';
		}
		window.setTimeout("closeDiv();", 2000);
		console.log(answers[question - 1]);
	}
	
	function closeDiv(){
		document.getElementById("message").style.display=" none";
		}
	
	function changeQn(element,question){
		console.log("hello");
		let temp = document.getElementById("box-2");
		temp.innerHTML = '<p>' + questions[question-1][0] + '</p>';
		temp.innerHTML += '<form id="answer">';
		for(let i = 1; i < 5; i++){
			temp.innerHTML += '<input type="radio" name="ans" id="option' + i + '" value="' + questions[question-1][i] + '"/>';
			temp.innerHTML += '<label for="ans' + i + '">' + questions[question-1][i] + '</label><br>';
		}
		temp.innerHTML += '<br><button onClick="saveAnswer(this,' + question + ')" class="save">Save Answer</button>';
		temp.innerHTML += '</form>';
	}
	
	function eval(){
		const ff = document.forms[0].appendChild(document.createElement("input"));
		ff.setAttribute("type","hidden");
		ff.setAttribute("name", "ans");
		ff.setAttribute("value", JSON.stringify(answers));
		document.forms[0].submit();
	}
	
</script>

<div id="grid-container">
<div id="box-1">
	<h3 align="center">Online Examination</h3>
	<form action="result.jsp" method="post">
		<p align="center">Roll No : <input name="rollno" type="text" placeholder="Enter your rollno">
	    Name : <input name="name" type="text" placeholder="Enter your name"></p>
		<p align="center">Subject Code : <input name="subcode" type="text" placeholder="Enter the subcode">
	    Subject Name : <input name="subname" type="text" placeholder="Enter the subject name"></p>
	</form>
</div>

<div id="box-2">
</div>
<div id="box-3">
<button class="qn" id="1" type="" onclick="changeQn(this,1)">1</button>
</div>
<div id="box-3">
<button class="qn" id="2" type="" onclick="changeQn(this,2)">2</button>
</div> 
<div id="box-3">
<button class="qn" id="3" type="" onclick="changeQn(this,3)">3</button>
</div> 
<div id="box-3">
<button class="qn" id="4" type="" onclick="changeQn(this,4)">4</button>
</div> 
<div id="box-3">
<button class="qn" id="5" type="" onclick="changeQn(this,5)">5</button>
</div>
<div id="box-3">
<button class="qn" id="6" type="" onclick="changeQn(this,6)">6</button>
</div>
<div id="box-3">
<button class="qn" id="7" type="" onclick="changeQn(this,7)">7</button>
</div>
<div id="box-3">
<button class="qn" id="8" type="" onclick="changeQn(this,8)">8</button>
</div> 
<div id="box-4">
<button class="qn" type="submit" onclick="eval()">Submit</button>
</div> 
</div>
</body>
</html>