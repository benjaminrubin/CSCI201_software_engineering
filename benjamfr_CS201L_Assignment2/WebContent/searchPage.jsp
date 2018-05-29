<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	/* What the user is searching for */
	String searchvalue = request.getParameter("searchvalue");
	searchvalue = searchvalue.trim();
	/* The attribute we are searching along */
	String attribute = request.getParameter("attribute");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate - Results</title>

<link rel="stylesheet" type="text/css" href="searchPageStyle.css">
</head>
<body onload="updatefield(); bookSearch();">
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
	<table>
	</table>
	</div>
</body>
<!-- enable JQuery -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script>
	//Updates the searchbox to keep the value that was searched for previously
	function updatefield() {
		if ("<%= searchvalue %>" != ""){
			document.querySelector("#searchbox").value = "<%= searchvalue %>";	
		}
	} /* end of function */
	
var bookz;
	
	function booksCallback(bookResults){
		
		for(var i = 0; i < bookResults.items.length; i++){
			/* create row */
			if(i%4 == 0){
				var row = document.createElement("tr");	
			}
					/* create a datacell */
					var bookCell = document.createElement("td");
					bookCell.className = "book";
					bookCell.title = bookResults.items[i].id;
					var linkCell = document.createElement("a");
					linkCell.href = "bookPage.jsp?id=" + bookResults.items[i].id + "&search=" + "<%= searchvalue %>";
					
					var bookImage = document.createElement("img");
					if(bookResults.items[i].volumeInfo.hasOwnProperty('imageLinks')){
						bookImage.src = bookResults.items[i].volumeInfo.imageLinks.thumbnail;
						linkCell.appendChild(bookImage);
					}
					
					var bookName = document.createElement("h1");
					bookName.innerHTML = bookResults.items[i].volumeInfo.title;
					
					var authorName = document.createElement("h2");
					authorName.innerHTML =  "by " + bookResults.items[i].volumeInfo.authors;
					
					linkCell.appendChild(bookImage);
					linkCell.appendChild(bookName);
					linkCell.appendChild(authorName);
					bookCell.appendChild(linkCell);
					row.appendChild(bookCell);			
					
				document.getElementById("results").appendChild(row);	
		}
	}
	
	function bookSearch() {
		var search = "<%= searchvalue %>";
		/* Build the url with user input */
		var scriptTag = document.createElement("script");
		scriptTag.src = "https://www.googleapis.com/books/v1/volumes?q=" + "<%= attribute %>:" + search + "&maxResults=12&callback=booksCallback";
		
		/* Remove any results that were there previously */
		var results = document.querySelector("#results");
		/* if results has child nodes, remove them */
		while(results.hasChildNodes()){
			results.removeChild(results.firstChild);
		}
		document.body.appendChild(scriptTag);
	}

</script>
</html>
