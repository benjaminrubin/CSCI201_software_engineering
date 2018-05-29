<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate</title>
<link rel="stylesheet" type="text/css" href="homePageStyle.css">
<!-- <style>
	
	html, body {
		padding: 0;
		margin: 0;
		font-family: Roboto;
		font-size: 20px;
		font-weight: bold;
	}
	a {
		style: none;
	}
	body {
		background-color: #53c653;
	}

	#container {
		margin-top: 180px;
		padding: 70px 0px;
		background-color: #0099ff;
	}

	table {
		margin:0 auto;
	}
	table td {
		padding-right: 155px;
	}

	input[type="text"]{
		display: block;
		margin-bottom: 25px;
		width: 440px;
		margin:0 auto;
		padding: 5px 10px;
		font-family: Roboto;
		font-size: 20px;
	}

	h1 {
		text-align: center;
		font-size: 100px;
		font-family: Roboto;
		color: white;
		margin: 5px 0px;
		text-shadow: -3px 0 black, 0 3px black, 3px 0 black, 0 -3px black;
	}

	#searchbox {
		position: relative;
	}

</style> -->
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
		</div>
	</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
</body>
</html>