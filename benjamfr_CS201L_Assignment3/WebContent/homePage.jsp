<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate</title>
<link rel="stylesheet" type="text/css" href="homePageStyle.css">
</head>
<body>
	<div id="container">
		<a href="homePage.jsp" style="text-decoration: none"><h1>LibraMate</h1></a>
		<div id="searchbox">
			<form name="search" method="GET" action="searchPage.jsp">
			<table>
				<tr>
					<input type="text" name="searchvalue" placeholder="Search..."/>
				</tr>
				<tr>
					<td><input type="radio" name="attribute" value="intitle" id ="r1" checked>Title</td>
					<td><input type="radio" name="attribute" value="isbn" id ="r2">ISBN</td>
				</tr>
				<tr>
					<td><input type="radio" name="attribute" value="inauthor" id ="r3">Author</td>
					<td><input type="radio" name="attribute" value="insubject" id ="r4">Genre</td>
				</tr>
			</table>
			</form>
		</div> <!-- end of search box -->
		<div id="signup"> <!-- beginning of sign up buttons -->
			<a href="signupPage.jsp" class="button">SIGN UP</a><br/>
			<a href="loginPage.jsp" class="button">LOGIN</a>
		</div>
	</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js">
var username = "<%= session.getAttribute("username") %>";
console.log("username is now: " + username);

</script>
<%
	session = request.getSession(false);
	session.invalidate();
	session = request.getSession(true);
%>


</body>
</html>