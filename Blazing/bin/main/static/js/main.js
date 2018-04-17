// check login
function checkLogin() {
	return false;
}

// login
function login(e) {
	e.preventDefault();
	// temp - change to display loading
	$("#signup-button").attr("disabled", true);
	$("#signup-button").css("background-color","white");
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
		},
		complete : function() {
			$("#login-button").attr("disabled", false);
			$("#login-button").css("background-color","#6c757d");	
		}
	});
}

//logout
function logout(){
	// remove user from session
}

//sign up
function signup(e) {
	e.preventDefault();
	// temp - change to display loading
	$("#signup-button").attr("disabled", true);
	$("#signup-button").css("background-color","white");
	var firstName = $("#firstname-signup").val();
	var lastName = $("#lastname-signup").val();
	var email = $("#email-signup").val();
	var password = $("#password-signup").val();
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
		},
		complete : function() {
			// temp - change to display loading
			$("#signup-button").attr("disabled", false);
			$("#signup-button").css("background-color","#6c757d");
		}
	});
}

// search
function search(e){
	var search = {};
	search["searchString"] = searchString;
	var jsonString = JSON.stringify(search);
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/search",
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

$(function() {
	// load header and scripts for header
	$("header").load("header.html", function() {
		var isLoggedIn = checkLogin();
		if (isLoggedIn) {
			$("#logged-in").show();
			$("#not-logged-in").hide();
		}else{
			$("#not-logged-in").show();
			$("#logged-in").hide();
		}
		$("#login-button").on("click", login);
		$("#logout-button").on("click", logout);
		$("#signup-button").on("click", signup);
		$("#search-button").on("click", function(){
			if ($("#search-input").val() != ""){
				search($("#search-input").val());
			}
		});
		$("#search-input").keypress(function(e){
			if (e.which == 13 && $(this).val() != ""){
				search($(this).val());
			}
		});
	});

	$("footer").load("footer.html");

	$(".section-carousel").slick({
		infinite: false,
		variableWidth: true,
		swipeToSlide: true,
		autoplay: false
	});
	$(".section-carousel.infinite-scroll").slick('unslick');
	$(".section-carousel.infinite-scroll").slick({
		infinite: true,
		variableWidth: true,
		centerMode: true,
		swipeToSlide: true,
		autoplay: true,
		autoplaySpeed: 4000
	});
});