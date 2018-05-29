<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="benjamfr_CS201L_Assignment3.UserList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate - Results</title>

<link rel="stylesheet" type="text/css" href="searchPageStyleUser.css">
<style>
	#profileAvatar img {
		align-items: center;
		border-radius: 50%;
		width: 90px;
		height: 90px;
		border: black 2px solid;
		margin-right: 10px;
		object-fit: cover;
	}
</style>
</head>
<body onload="updatefield();">

	<div id="genheader">
		<form name="search" method="GET" action="searchPageUser.jsp">
		<input id="type" name="formversion" value="" type="hidden">
			<div id="container">
				<!-- the logo -->
				<div id="lheader"> <a href="homePage.jsp" style="text-decoration: none"><h1>LibraMate</h1></a> </div>
				<!-- beginning of form -->
				<!-- the searchbar -->
				<div id ="mheader">
					<input type="text" name="searchvalue" placeholder="Search books..." display="inline" id="searchbox">
					<img src="images/book_icon.png" id="search_icon" width="40px" onclick="update_icon();">
				</div>
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
				<!-- profile image -->
					<div>
					<a href="#" id="profileAvatar"></a>
					</div>
			</div>
		</form>
	</div>
	<!-- all the results in the page -->
	<div id="results" class="">
	<table>
	</table>
	</div>
</body>
<!-- enable JQuery -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script>
	
	var username = "<%= session.getAttribute("username") %>";
	console.log("username is now: " + username);
	
	document.getElementById("profileAvatar").href = "userPage.jsp?id=" + username; 

	//this function returns the value of the user/book icon
	function getbutton() {
		if(document.getElementById("search_icon").src == "http://localhost:8080/benjamfr_CS201L_Assignment3A/images/book_icon.png"){
			return "books";
		}
		else{
			return "users";
		}
	}
	
	//this function sets the value of the user/book icon
	function setbutton(str) {
		//set the button to users
		if(str == "users"){
			console.log("we are searching for users");
			document.getElementById("search_icon").src="images/user_icon.png";
			document.getElementById("rheader").style.visibility = "hidden";
			document.getElementById("searchbox").placeholder = "Search users...";
			document.getElementById("type").value = "users";			
		}
		//set the buttton to books
		else if (str == "books") {
			console.log("we are searching for books");
			document.getElementById("search_icon").src="images/book_icon.png";
			document.getElementById("rheader").style.visibility = "visible";
			document.getElementById("searchbox").placeholder = "Search books...";
			document.getElementById("type").value = "books";
		}
	}
	
	//this function runs when clicking on the user/book icon
	function update_icon() {
		//clear the searchbox first
		document.getElementById("searchbox").value = "";
		if(getbutton() == "books") {
			setbutton("users");
			}
		else {
			setbutton("books");
			}
	}
	
	var bookz;
	var searchvalue = "";	
	
	//obtain the search value, attribute or form type
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
	//this is specifically used for the searchvalue
	function urldecode(str) {
		   return decodeURIComponent((str+'').replace(/\+/g, '%20'));
	}
	
	
	//this function updates the searchfield on page load
	function updatefield() {
		
		<% UserList userlist = (UserList) session.getAttribute("userlist"); %>
			/* String username = session.getAttribute("username");  */
		
		
		var profile_image = "<%= userlist.getImage((String)session.getAttribute("username")) %>";
		console.log("profile image is now: " + profile_image);
		
		var profileAvatar = document.createElement("img");
		profileAvatar.src = profile_image;
		document.getElementById("profileAvatar").appendChild(profileAvatar);
		
		var attribute = getQueryVariable("attribute");
		var formtype = getQueryVariable("formversion");
		console.log("formtype is: " + formtype);
		
		
		
		//we start off searching for books
		if (formtype == ""){
			setbutton("books");
			formtype = "books";
		}
		
		/* console.log("value of the form is: " + document.getElementsByName("formversion").value); */
	
		
		searchvalue = urldecode(getQueryVariable("searchvalue"));
		document.querySelector("#searchbox").value = searchvalue;	
		
		//update the type of search based on the form type
		if(formtype == "books"){
			setbutton("books");
			
			//get the type of attribute that was searched for the book
			var attributes = document.getElementsByName('attribute');
			var attributeval;
			for (var i = 0; i < attributes.length; i++){
			    if(attributes[i].value == attribute){
			    		attributes[i].checked = true;	
			    }
			}
			bookSearch();
		}
		
		//we are searching for users
		else{
			console.log("we are searching for users");
			setbutton("users");
			userSearch();
		}
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
					linkCell.href = "bookPageUser.jsp?id=" + bookResults.items[i].id + "&search=" + searchvalue;
					
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
		results.className += " bookResults";
		/* if results has child nodes, remove them */
		while(results.hasChildNodes()){
			results.removeChild(results.firstChild);
		}
		
		//what is the search value?
		var searchvalue = urldecode(getQueryVariable("searchvalue"));
		var attribute = getAttribute();
		
		
		if (searchvalue != ""){
			/* Build the url with user input */
			var scriptTag = document.createElement("script");
			console.log("https://www.googleapis.com/books/v1/volumes?q=" + attribute + searchvalue + "&maxResults=12&callback=booksCallback");
			scriptTag.src = "https://www.googleapis.com/books/v1/volumes?q=" + attribute + searchvalue + "&maxResults=12&callback=booksCallback";
			document.body.appendChild(scriptTag);
		}
	}
	
	
	//this function conducts the usersearch
	function userSearch() {
		console.log("conducting user search");
		
		/* Remove any results that were there previously */
		var results = document.querySelector("#results");
		results.className += " userResults";
		
		/* if results has child nodes, remove them */
		while(results.hasChildNodes()){
			results.removeChild(results.firstChild);
		}

		
		//what is the search value (username being searched for)?
		var searchvalue = urldecode(getQueryVariable("searchvalue"));
		
		//if the search value is an empty string, just return all the users
		var all = false;
		if (searchvalue == "") {
			//return all users
			all = true;
		}
		//request the json file
		var xhr = new XMLHttpRequest();			
		xhr.open('GET', "http://localhost:8080/benjamfr_CS201L_Assignment3A/UserList.json");
		xhr.send();
		
		xhr.onreadystatechange = function() {
			/* console.log(xhr.readyState); */
			if(xhr.readyState == XMLHttpRequest.DONE) {
				//status codes
				if(xhr.status == 200){
				// console.log(xhr.responseText);
				//JSON.parse converts strings into JSON
 				console.log(xhr.response);
				/*console.log(JSON.parse(xhr.responseText)); */
				var userlist = JSON.parse(xhr.responseText);
				console.log("size is: " + userlist.users.length);
				
				var count = 0;
				
				for(var i = 0; i < userlist.users.length; i++){
					
/* 					//for every four elements add another row
					if(count%4 == 0){
						var row = document.createElement("tr");	
						} */
					//if the searchvalue is a substring of a username, display it
					console.log("Im about to going");
					console.log("all is " + Boolean(all));
					
					
					if(userlist.users[i].username.indexOf(searchvalue.toLowerCase()) != -1 || all){
						console.log("yay im in");
							/* create a datacell */
							var userCell = document.createElement("div");
							userCell.className = "user";
							userCell.title = userlist.users[i].username;
							var linkCell = document.createElement("a");
							linkCell.href = "userPage.jsp?id=" + userlist.users[i].username;
							
							var userImage = document.createElement("img");
							if(userlist.users[i].hasOwnProperty('imageURL')){
								userImage.src = userlist.users[i].imageURL;
								linkCell.appendChild(userImage);
							}
							
							var userName = document.createElement("h3");
							userName.innerHTML = "@" + userlist.users[i].username;
	
							linkCell.appendChild(userImage);
							linkCell.appendChild(userName);
							userCell.appendChild(linkCell);
							
							//append the row if it's full of 4 users
							document.getElementById("results").appendChild(userCell);	
							count++;
						}
					}
				if(count == 0){
					var noUserCell = document.createElement("h1");
					noUserCell.innerHTML = "No Users Found!";
					document.getElementById("results").appendChild(noUserCell);
				}
				
				
				}

			}
			else{
				/* alert("AJAX Error!!! Error status: " + xhr.statusText); */
			}
		}		
	}
	
</script>
</html>
