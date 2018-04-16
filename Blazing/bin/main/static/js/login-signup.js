// login and signup system
function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}

function checkLogin() {
	return false;
}

// status codes:
// 0: success
// 1: invalid email: email is used
// 2: invalid email: email is empty
// 3: invalid email: does not contain @ and .
// 4: invalid password: password not within character length range
// 5: invalid password: password is empty
// 99: error
function login(email, password) {
	var loginCredentials = {};
	loginCredentials["email"] = email;
	loginCredentials["password"] = password;
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/login",
		data : JSON.stringify(loginCredentials),
		cache : false,
		dataType : 'json',
		timeout : 10000,
		success : function(data) {
			return 0;
		},
		error : function(e) {
			console.log("ERROR: ", e);
			return 99;
		}
	});
}

function logout() {
//	document.cookie="email=;expires=Thu, 01 Jan 1970 00:00:00 UTC;";
}

// status codes:
// 0: success
// 1: invalid email: email is used
// 2: invalid email: email is empty
// 3: invalid email: does not contain @ and .
// 4: invalid password: password not within character length range
// 5: invalid password: password is empty
// 6: first name empty
// 7: last name empty
// 99: error
function signup(fn, ln, em, pw){
	var registerCredentials = {"firstName":fn, "lastName":ln, "email":em, "password":pw};
//	var registerCredentials = {};
	var jsonString = JSON.stringify(registerCredentials);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/register",
		data : jsonString,
		cache : false,
		success : function(data) {
			console.log("success");
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
}