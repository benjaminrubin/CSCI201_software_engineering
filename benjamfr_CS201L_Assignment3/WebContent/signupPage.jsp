<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Signup Page</title>
<link rel="stylesheet" type="text/css" href="signupPageStyle.css">
<style>
#button {
-webkit-appearance:default-button;
    background-color: #002db3 !important;
    border: none !important;
    color: white !important;
    font-family: Roboto !important;
    font-weight: bold !important;
    padding: 15px 3px !important;
    width: 200px !important;
    text-align: center !important;
    text-decoration: none !important;
    display: inline-block !important;
    font-size: 16px !important;
    margin: 4px 6px !important;
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
		<h1>Sign Up</h1>
		<div id="searchbox">
			<%
			String userError = (String)session.getAttribute("userError");
			String passError = (String)session.getAttribute("passError");
			String imgError = (String)session.getAttribute("imgError");
			
			if (userError == null || userError.equals("null")) {userError = "";}
			
			if (passError == null || passError.equals("null")) {passError = "";}
			
			if (imgError == null || imgError.equals("null")) {imgError = "";}
			
			%>
			<form name="signup" method="POST" action="ValidateSignup">
			Username
			<input class="gimmespace" type="text" name="username" id="usernameid"/> 
			<font color="red"><strong><%= userError %></strong></font><br />
			Password
			<input class="gimmespace" type="password" name="password"/>
			<font color="red"><strong><%= passError %></strong></font><br />
			Image URL
			<input class="gimmespace" type="text" name="imageurl" id="imgid"/>
			<font color="red"><strong><%= imgError %></strong></font><br />
			
			
		<div id="signup"> <!-- beginning of sign up buttons -->
			<input type="submit" name="submit" value="CREATE ACCOUNT" id="button"/>
		</div>
		</form>
	</div> <!-- end of search box -->
	</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script>

//verify the image url



<%
/* What the username was */
String olduser = request.getParameter("username");
/* what the img url was */
String oldimg = request.getParameter("imageurl");
%>

console.log("the error is: " + "<%= userError %>");

//Updates the form fields to keep the values used previously
function updatefields() {	
	if ("<%= olduser %>" != "null"){
		document.querySelector("#usernameid").value = "<%= olduser %>";	
	}
	console.log("img is: " + "<%= oldimg %>");
	if ("<%= oldimg %>" != "null"){
		document.querySelector("#imgid").value = "<%= oldimg %>";	
	}
} /* end of function */
</script>
</body>
</html>