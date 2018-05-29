<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate - Results</title>
<link rel="stylesheet" type="text/css" href="bookPageStyle.css">

<!-- <style>
html, body {
		padding: 0;
		margin: 0;
		font-family: Roboto;
		font-size: 20px;
		font-weight: bold;
	}

	body {
		background-color: #0099ff;
	}

	/*the general container for the page*/
	#container {

	}

	#genheader {
		position: fixed;
		line-height: 130px; 
		height: 130px;
		background-color: #53c653;
		width: 100%;
	}

	/*the object holding the LibraMate logo*/
	#lheader {
		position: relative;
		text-align: center;
		float: left;
		width: 20%;
		height: 100%;
	}

	#lheader h1 {
		position: relative;
		display: inline;
		font-size: 60px;
		font-family: Roboto;
		color: white;
		text-shadow: -3px 0 black, 0 3px black, 3px 0 black, 0 -3px black;
	}
	/*The object holding the searchbar*/
	#mheader {
		position: relative;
		text-align: center;
		float: left;
		width: 60%;
		height: 100%;
		margin-top: 50px;
	}
	/*the design of the searchbar*/
	input[type="text"]{
		
		width: 95%;
		padding: 5px 10px;
		font-family: Roboto;
		font-size: 20px;

	}

	/*the object holding the radio buttons*/
	#rheader {
		margin-top: 20px;
		position: relative;
		float: left;
		width: 20%;
		line-height: 100%;
	}

	#rheader td {
		line-height: 40px;
	}

	/*content below the header*/
	#results {
		padding-top: 170px;
		/* padding-right: 50px;
		padding-left: 50px; */
		margin-left: auto;
		margin-right: auto;
	}
	
	h1 {
		font-size: 32px;
		margin-bottom: 0px;
	}
	

	#left {
		float: left;
		width: 30%;
		margin: 0px auto;
		text-align: center;
	}
	
	#left img {
		width: 80%;
	}
	
	#right{
		float: left;
		width: 68%;
	}
	
	#right h1 {
		/* font-size: 50px; */
		font-family: Roboto;
		color: white;
	   -webkit-text-stroke: 2px black;
	}

	#right h2 {
		/* font-size: 50px; */
		font-family: Roboto;
		color: white;
		-webkit-text-stroke: 2px black;
	}
	
	#right h3 {
		/* font-size: 50px; */
		font-family: Roboto;
		color: white;
		-webkit-text-stroke: 2px black;
	}
	
	.star {
		width: 50px;
	}

</style> -->
</head>
<body onload="bookED();">
	<div id="container">
		<div id="genheader">
			<!-- first object -->
			<div id="lheader">
				<a href="homePage.jsp" style="text-decoration: none"><h1>LibraMate</h1></a>
			</div>
			<form name="search" method="GET" action="searchPage.jsp">
				<!-- second object -->
				<div id="mheader">
					<!-- <input type="text" id="searchbox" name="searchvalue" placeholder=" Search" display="inline" id="magnifier"> -->
					<input type="text" name="searchvalue" placeholder="Search..." display="inline" id="searchbox">
				</div>
				<!-- third object -->
				<div id="rheader">
					<table>
						<tr>
							<td><input type="radio" name="attribute" value="intitle" id="r1" checked>Title</td>
							<td><input type="radio" name="attribute" value="isbn" id="r2">ISBN</td>
						</tr>
						<tr>
							<td><input type="radio" name="attribute" value="inauthor" id="r3">Author</td>
							<td><input type="radio" name="attribute" value="insubject" id="r4">Genre</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<!-- all the results in the page -->
	<div id="results">
	</div>
</body>
<!-- enable JQuery -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script>
//Updates the searchbox to keep the value that was searched for previously

	function updatefield(something) {
		document.querySelector("#searchbox").value = something;	
	} /* end of function */

	//obtain the id of the book, and the search values used beforehand
	function getQueryVariable(variable)
	{
	       var query = window.location.search.substring(1);
	       var vars = query.split("&");
	       for (var i=0;i<vars.length;i++) {
	               var pair = vars[i].split("=");
	               if(pair[0] == variable){return pair[1];}
	       }
	       return(false);
	}
	
	var ID = getQueryVariable("id");
	var searchterm = getQueryVariable("search");
	function urldecode(str) {
		   return decodeURIComponent((str+'').replace(/\+/g, '%20'));
		}
	var searchterm = urldecode(searchterm);
	updatefield(searchterm);
	
	function bookED() {	
		var URL = "https://www.googleapis.com/books/v1/volumes/" + ID;
		var request = new XMLHttpRequest();
		request.open('GET', URL);
		request.responseType = 'json';
		request.send();
		//onload update the page with the relevant data
		request.onload = function() {
			var data = request.response;
			

			var left = document.createElement("div");
			left.id = "left";
			var bookImage = document.createElement("img");
			
			if(data.volumeInfo.hasOwnProperty('imageLinks')){
				bookImage.src = data.volumeInfo.imageLinks.thumbnail;
				left.appendChild(bookImage);
			}
			var right = document.createElement("div");
			right.id = "right";
			var bookTitle = document.createElement("h1");
			bookTitle.innerHTML = data.volumeInfo.title;
			var authorName = document.createElement("h2");
			authorName.innerHTML = "by " + data.volumeInfo.authors;
			var publisher = document.createElement("h3");
			publisher.innerHTML = "Publisher: " + data.volumeInfo.publisher;
			var description = document.createElement("h3");
			description.innerHTML = "Description:";
			var text = document.createElement("p");
			text.innerHTML = data.volumeInfo.description;
			var rating = document.createElement("h3");
			rating.innerHTML = "Rating:";
			var stars = document.createElement("div");
			var verdict = data.volumeInfo.averageRating;
			
			if(!verdict){
				
			}
			else{				
				for(var i = 0; i < 5; i ++){
					var star = document.createElement("img");
					star.classList.add("star");
					if (verdict >= 1){
						star.src = "fullstar-01.png";
					}
					else if (verdict > 0){
						star.src = "halfstar-01.png";
					}
					else {
						star.src = "nostar-01.png";
					}
					stars.appendChild(star);
					verdict -= 1;
				}
			}
			
			right.appendChild(bookTitle);
			right.appendChild(authorName);
			right.appendChild(publisher);
			right.appendChild(description);
			right.appendChild(text);
			right.appendChild(rating);
			right.appendChild(stars);
				

			document.getElementById("results").appendChild(left);
			document.getElementById("results").appendChild(right);

		/* 	console.log(data.volumeInfo.title);
			console.log(data.volumeInfo.authors);
			console.log(data.volumeInfo.publisher);
			console.log(data.volumeInfo.description);
			console.log(data.volumeInfo.averageRating); */

			}
	}
	
</script>
</html>
