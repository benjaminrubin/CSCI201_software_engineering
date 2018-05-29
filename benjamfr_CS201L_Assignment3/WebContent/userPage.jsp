<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="benjamfr_CS201L_Assignment3.UserList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LibraMate - Results</title>
<link rel="stylesheet" type="text/css" href="userPageStyle.css">
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

button {
	-webkit-appearance: default-button;
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

button:hover {
    background-color: #3D66CA !important;
}

.active {
	background-color: #3D66CA !important;
}

</style>
</head>
<body onload="populate(); updateavatar();">


	<div id="genheader">
		<form name="search" method="GET" action="searchPageUser.jsp">
			<input id="type" name="formversion" value="" type="hidden">
			<div id="container">
				<!-- the logo -->
				<div id="lheader">
					<a href="homePage.jsp" style="text-decoration: none"><h1>LibraMate</h1></a>
				</div>
				<!-- beginning of form -->
				<!-- the searchbar -->
				<div id="mheader">
					<input type="text" name="searchvalue" placeholder="Search books..."
						display="inline" id="searchbox"> <img
						src="images/user_icon.png" id="search_icon" width="40px"
						onclick="update_icon();">
				</div>
				<!-- the buttons -->
				<div id="rheader" style="visibility: hidden">
					<table>
						<tr>
							<td><input type="radio" name="attribute" value="intitle"
								id="r1" checked>Title</td>
							<td><input type="radio" name="attribute" value="isbn"
								id="r2">ISBN</td>
						</tr>
						<tr>
							<td><input type="radio" name="attribute" value="inauthor"
								id="r3">Author</td>
							<td><input type="radio" name="attribute" value="insubject"
								id="r4">Genre</td>
						</tr>
					</table>
				</div>
				<!-- profile image -->
				<div>
					<a href="" id="profileAvatar"></a>
				</div>
			</div>
		</form>
	</div>
	<!-- all the results in the page -->
	<div id="general_container">
		<div id="top_container">
			<div id="profile_container">
				<img id="profile_container_avatar" src="">
			</div>
			<div>
				<h1 id="usertitle"></h1>
			</div>
		</div>
		<div id="bottom_container">
			<div class="tab">
				<button class="tablinks" onclick="showme(event, 'read')" id="defaultOpen">Read</button>
				<button class="tablinks" onclick="showme(event, 'favorites')">Favorites</button>
			</div>
			<div id="read" class="tabcontent">
			</div>

			<div id="favorites" class="tabcontent">
			</div>
		</div>
	</div>
</body>
<!-- enable JQuery -->
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script>

var username = "<%=session.getAttribute("username")%>";
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

	function updateavatar() {	
		
		<%UserList userlist = (UserList) session.getAttribute("userlist");%>
		/* String username = session.getAttribute("username");  */
	
		var profile_image = "<%=userlist.getImage((String) session.getAttribute("username"))%>";
		console.log("profile image is now: " + profile_image);

		var profileAvatar = document.createElement("img");
		profileAvatar.src = profile_image;
		document.getElementById("profileAvatar").appendChild(profileAvatar);
		

	} /* end of function */

	//obtain the user we are looking at
	function getQueryVariable(variable) {
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i = 0; i < vars.length; i++) {
			var pair = vars[i].split("=");
			if (pair[0] == variable) {
				return pair[1];
			}
		}
		return (false);
	}

	function urldecode(str) {
		return decodeURIComponent((str + '').replace(/\+/g, '%20'));
	}

	var userID = urldecode(getQueryVariable("id"));
	console.log("user we're looking at is: " + userID);

	//this function conducts the usersearch
	function populate() {
		console.log("conducting user search");
		var count = 4;
		
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
				console.log("Printing the JSON we found from the server:");
 				console.log(xhr.response);
				/*console.log(JSON.parse(xhr.responseText)); */
				var userlist = JSON.parse(xhr.responseText);
				console.log("size is: " + userlist.users.length);
				
				
				var userIDindex;
				//get the user
				for (var i = 0; i < userlist.users.length; i++) {
					if (userlist.users[i].username == userID) {
						userIDindex = i;
						break;
					}
				}

				//profile image of user profile
				document.getElementById("profile_container_avatar").src = userlist.users[userIDindex].imageURL;
				
				if (userID == username){
					document.getElementById("usertitle").innerHTML = "Your Library";
				}
				else{
					document.getElementById("usertitle").innerHTML = userlist.users[userIDindex].username + "'s Library";	
				}
				
				
				//populate the reading
					for (var j = 0; j < userlist.users[userIDindex].library.read.length; j++){
						console.log(userlist.users[userIDindex].library.read[j]);
						document.getElementById("read").innerHTML += (userlist.users[userIDindex].library.read[j] + '<br>');
					}
				//populate the favorites
					for (var k = 0; k < userlist.users[userIDindex].library.favorite.length; k++){
						console.log(userlist.users[userIDindex].library.favorite[k]);
						document.getElementById("favorites").innerHTML += (userlist.users[userIDindex].library.favorite[k] + '<br>');
					}
				
				}
			}
			else{
				/* alert("AJAX Error!!! Error status: " + xhr.statusText); */
			}
		}		
	}
	


	
	function showme(evt, tabName) {
	    // Declare all variables
	    var i, tabcontent, tablinks;

	    // Get all elements with class="tabcontent" and hide them
	    tabcontent = document.getElementsByClassName("tabcontent");
	    for (i = 0; i < tabcontent.length; i++) {
	        tabcontent[i].style.display = "none";
	    }

	    // Get all elements with class="tablinks" and remove the class "active"
	    tablinks = document.getElementsByClassName("tablinks");
	    for (i = 0; i < tablinks.length; i++) {
	        tablinks[i].className = tablinks[i].className.replace(" active", "");
	    }

	    // Show the current tab, and add an "active" class to the button that opened the tab
	    document.getElementById(tabName).style.display = "block";
	    evt.currentTarget.className += " active";
	}
	
	console.log("work bitch" + document.getElementById("defaultOpen").innerHTML);
	document.getElementById("defaultOpen").click();
	
</script>
</html>
