<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate - Results</title>

<link rel="stylesheet" type="text/css" href="searchPageStyle.css">
</head>
<body onload="updatefield();">

	<div id="genheader">
		<form name="search" method="GET" action="searchPage.jsp">
			<div id="container">
				<!-- the logo -->
				<div id="lheader"> <a href="homePage.jsp" style="text-decoration: none"><h1>LibraMate</h1></a> </div>
				<!-- beginning of form -->
				<!-- the searchbar -->
				<div id ="mheader"> <input type="text" name="searchvalue" placeholder="Search..." display="inline" id="searchbox"> </div>
				<!-- the buttons -->
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
			</div>
		</form>
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
	
	var username = "<%= session.getAttribute("username") %>";
	console.log("username is now: " + username);
	
	var bookz;
	var searchvalue = "";	
	
	//obtain the search value used beforehand and the attribute
	function getQueryVariable(variable)
	{
	       var query = window.location.search.substring(1);
	       var vars = query.split("&");
	       for (var i=0;i<vars.length;i++) {
	               var pair = vars[i].split("=");
	               if(pair[0] == variable){return pair[1];}
	       }
	       return "";
	}
	
	//this function removes the %20 terms to make the search term readable
	function urldecode(str) {
		   return decodeURIComponent((str+'').replace(/\+/g, '%20'));
	}
	
	//this function updates the searchfield on page load
	function updatefield() {
		
		var attribute = getQueryVariable("attribute");
		searchvalue = urldecode(getQueryVariable("searchvalue"));

		document.querySelector("#searchbox").value = searchvalue;	
		
		var attributes = document.getElementsByName('attribute');
		var attributeval;
		for(var i = 0; i < attributes.length; i++){
		    if(attributes[i].value == attribute){
		    		attributes[i].checked = true;	
		    }
		}
		
		bookSearch();
	} /* end of function */


	
	//this function returns the value of the chosen attribute
	function getAttribute() {
		var attributes = document.getElementsByName('attribute');
		var attributeval;
		for(var i = 0; i < attributes.length; i++){
		    if(attributes[i].checked){
		    	return attributes[i].value;	
		    }
		}
		return attributeval;
	}
	

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
					linkCell.href = "bookPage.jsp?id=" + bookResults.items[i].id + "&search=" + searchvalue;
					
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
	
	//this function conducts the booksearch
	function bookSearch() {
		
		/* Remove any results that were there previously */
		var results = document.querySelector("#results");
		/* if results has child nodes, remove them */
		while(results.hasChildNodes()){
			results.removeChild(results.firstChild);
		}
		
		//what is the search value?
		var searchvalue = urldecode(getQueryVariable("searchvalue"));
		var attribute = getAttribute();
		
		console.log("search value is " + searchvalue);
		console.log("attribute is " + attribute);
		
		if (searchvalue != ""){
			/* Build the url with user input */
			var scriptTag = document.createElement("script");
			console.log("https://www.googleapis.com/books/v1/volumes?q=" + attribute + searchvalue + "&maxResults=12&callback=booksCallback");
			scriptTag.src = "https://www.googleapis.com/books/v1/volumes?q=" + attribute + searchvalue + "&maxResults=12&callback=booksCallback";
			document.body.appendChild(scriptTag);
		}
	
		

	}
	
	


</script>
</html>
