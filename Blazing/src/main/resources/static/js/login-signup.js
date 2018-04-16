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
	var x = document.cookie;
	var emailCookie = getCookie("email");
	if (emailCookie != ""){
		return true;
	}else{
		return false;
	}
}

// status codes:
// 0: success
// 1: invalid email: email is used
// 2: invalid email: email is empty
// 3: invalid email: does not contain @ and .
// 4: invalid password: password not within range
// 5: invalid password: password is empty
// 99: error
function login(email, password) {
	if (email == null){
		return 2;
	}
	if (password == null){
		return 5;
	}
	var loginCredentials = {};
	loginCredentials{"email"} = email;
	loginCredentials{"password"} = password;
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/login",
		data : JSON.stringify(loginCredentials),
		cache : false,
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			if (data == true){
				return 0;
			}else{
				return 1;
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
			return 99;
		}
	});
}

function logout() {
	document.cookie="email=;expires=Thu, 01 Jan 1970 00:00:00 UTC;";
}

//status codes:
//0: success
//1: invalid email
//3: error
function signup(firstName, lastName, email, password){

}