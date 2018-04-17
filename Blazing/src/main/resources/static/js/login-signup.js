// login and signup system

// check if user is in session
// returns boolean
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
	var jsonString = JSON.stringify(loginCredentials);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/login",
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
// send json
// requestbody as separate variables

function logout() {
	//delete user from session
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
function signup(firstName, lastName, email, password){
	var registerCredentials = {}
	registerCredentials["firstName"] = firstName;
	registerCredentials["lastName"] = lastName;
	registerCredentials["email"] = email;
	registerCredentials["password"] = password;
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