<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="signupPageStyle.css">
<style>
#button {
-webkit-appearance:default-button;
    background-color: #002db3 !important;
    border: none !important;
    color: white !important;
    font-family: Roboto !important;
    font-weight: bold !important;
    padding: 15px 32px !important;
    width: 164px !important;
    text-align: center !important;
    text-decoration: none !important;
    display: inline-block !important;
    font-size: 16px !important;
    margin: 4px 2px !important;
    cursor: pointer !important;
}

input[type="password"] {
	-webkit-appearance: none;
	display: block;
	width: 320px;
	margin:0 auto;
	padding: 5px 10px;
	font-family: Roboto;
	font-size: 20px;
}
</style>
</head>
<body onload="updatefields();">
<div id="corner">
<a href="homePage.jsp" style="text-decoration: none"><h2>LibraMate</h2></a>
</div>
	<div id="container">
		<h1>Login</h1>
		<div id="searchbox">
			<%
			String userError = (String)session.getAttribute("userError");
			String passError = (String)session.getAttribute("passError");
			
			if (userError == null || userError.equals("null")) {userError = "";}
			
			if (passError == null || passError.equals("null")) {passError = "";}
			
			%>
			<form name="login" method="POST" action="ValidateLogin">
				Username
				<input class="gimmespace" type="text" name="username" id="usernameid"/>
				<font color="red"><strong><%= userError %></strong></font><br />
				Password
				<input class="gimmespace" type="password" name="password"/>
				<font color="red"><strong><%= passError %></strong></font><br />
			
		</div> <!-- end of search box -->
		<div id="signup"> <!-- beginning of sign up buttons -->
			<input type="submit" name="submit" value="LOGIN" id="button"/>
		</div>
	</form>
	</div>
</body>
<script>
<%
/* What the username was */
String olduser = request.getParameter("username");
%>

//Updates the form fields to keep the values used previously
function updatefields() {	
	if ("<%= olduser %>" != "null"){
		document.querySelector("#usernameid").value = "<%= olduser %>";	
	}
} /* end of function */
</script>
</html>