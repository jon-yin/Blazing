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

function login(email, password) {
	// probably use try-catch
	if (email == "a@a" && password == "a"){
		document.cookie="email="+email+";password="+password;
		location.reload();
		return 0;
	}
	else if (email != "a@a"){
		return 1;
	}
	else if (password != "a"){
		return 2;
	}
}

function logout() {
	document.cookie="email=;expires=Thu, 01 Jan 1970 00:00:00 UTC;";
}

function signup(firstName, lastName, email, password){
	try {
		return 0;
	}catch(err){
		return 1;
	}
}