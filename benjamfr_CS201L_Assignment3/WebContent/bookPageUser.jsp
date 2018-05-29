<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="benjamfr_CS201L_Assignment3.UserList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate - Results</title>
<link rel="stylesheet" type="text/css" href="bookPageStyleUser.css">
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
<body onload="bookED();">
	

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
	<div id="results">
	<div id="left">
		<img id="bookImage">
		<div id="dropdown">
		<button id="dropbtn">Add to library</button>
			<div id="dropdown-content">
				<a class="linkers" href="#" id="addRead">Read</a>
				<a class="linkers" href="#" id="addFav">Favorites</a>
			</div>
		</div>
	</div>
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
		document.getElementById("search_icon").src="images/user_icon.png";
		document.getElementById("rheader").style.visibility = "hidden";
		document.getElementById("searchbox").placeholder = "Search users...";
		document.getElementById("type").value = "users";			
	}
	//set the buttton to books
	else if (str == "books") {
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

//Updates the searchbox to keep the value that was searched for previously

	function updatefield(something) {
		document.querySelector("#searchbox").value = something;	
		
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
		
		//get the type of attribute that was searched for the book
		var attributes = document.getElementsByName('attribute');
		var attributeval;
		for (var i = 0; i < attributes.length; i++){
		    if(attributes[i].value == attribute){
		    		attributes[i].checked = true;	
		    }
		}
		
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
	
	var data;
	
	function bookED() {	
		
		var searchterm = urldecode(searchterm);
		updatefield(searchterm);
		
		var URL = "https://www.googleapis.com/books/v1/volumes/" + ID;
		var request = new XMLHttpRequest();
		request.open('GET', URL);
		request.responseType = 'json';
		request.send();
		//onload update the page with the relevant data
		request.onload = function() {
			data = request.response;
			
			if(data.volumeInfo.hasOwnProperty('imageLinks')){
				document.getElementById("bookImage").src = data.volumeInfo.imageLinks.thumbnail
			}
			
			var right = document.createElement("div");
			right.id = "right";
			var bookTitle = document.createElement("h1");
			bookTitle.id = "bookTitle";
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
						star.src = "images/fullstar-01.png";
					}
					else if (verdict > 0){
						star.src = "images/halfstar-01.png";
					}
					else {
						star.src = "images/nostar-01.png";
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


			}
		console.log(document.getElementById('addRead').innerHTML);
	}
	
	
	var links = document.querySelectorAll('.linkers');
	
	
	for(var i = 0; i < links.length; i++){
		links[i].onclick = function() {
			//get the booktitle and make sure we have the current user
			var bookTitle = document.getElementById('bookTitle').innerHTML;
			ourType = this.id;
			var xhr = new XMLHttpRequest();			
			xhr.open('GET', "http://localhost:8080/benjamfr_CS201L_Assignment3A/UserList.json");
			xhr.send();
			//open ajax call
			xhr.onreadystatechange = function() {
				if(xhr.readyState == XMLHttpRequest.DONE) {
					//status codes
					if(xhr.status == 200){
					//JSON.parse converts strings into JSON
					var userlist = JSON.parse(xhr.responseText);
					console.log("this is what is before any change: ");
					console.log(userlist);
					var userIDindex;
					//get the user index
						for (var i = 0; i < userlist.users.length; i++) {
							console.log("name: " + userlist.users[i].username);
							if (userlist.users[i].username == username) {
								userIDindex = i;
								break;
							}
						}
						//update the json file
						
						console.log("just for kicks: ");
						console.log(userlist.users[userIDindex].library);
						
						//if the user doesn't have any library or favorites, create empty arrays for them
						if(userlist.users[userIDindex].library === undefined){
							console.log("this guy isn't looking at any books");
							userlist.users[userIDindex]["library"] = {"read": [], "favorite": []};
							console.log(userlist.users[userIDindex].library);
						}
						//the user does have
						//add to read list
						if(ourType == "addRead") {
							console.log("adding to read");
							userlist.users[userIDindex].library.read.push(bookTitle);			
						}	//add to favorite list
						else{
							console.log("adding to favorite");
							userlist.users[userIDindex].library.favorite.push(bookTitle);
							console.log("this should be updated: " + userlist.users[userIDindex].library.read);
							console.log(userlist);
						}
						console.log("this should be updated: " + userlist.users[userIDindex].library.read);
						console.log(userlist);
						
						//send the data back to the server
						
						var sendme = JSON.stringify(userlist);
						sendme.replace(/\"/g, "");
						var request = new XMLHttpRequest();
						var url = "updateUserList.java";
						request.open("POST", "http://localhost:8080/benjamfr_CS201L_Assignment3A/updateUserList");
						request.setRequestHeader("Content-type", "application/json");
						request.onreadystatechange = function(){
							
						}
						request.send(sendme);

					}
				}
			}
		}
	}


	
	
</script>
</html>
