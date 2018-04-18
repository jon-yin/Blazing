var properties;

// check login
function checkLogin() {
//	$.ajax({
//		type : "POST",
//		contentType : "application/json",
//		url : "/login",
//		data : jsonString,
//		cache : false,
//		success : function(data) {
//			console.log("success");
//		},
//		error : function(e) {
//			console.log("ERROR: ", e);
//		},
//		complete : function() {
//			$("#login-button").attr("disabled", false);
//			$("#login-button").css("background-color","#6c757d");	
//		}
//	});
	return false;
}

// login
function login(e) {
	e.preventDefault();
	// temp - change to display loading
	var email = $("#email-login").val();
	var password = $("#password-login").val();
	$("#signup-button").attr("disabled", true);
	$("#signup-button").css("background-color","aaaaaa");
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
	$("#email-login").val("");
	$("#password-login").val("");
}

//logout
function logout(){
}

//sign up
function signup(e) {
	e.preventDefault();
	// temp - change to display loading
	$("#signup-button").attr("disabled", true);
	$("#signup-button").css("background-color","#aaaaaa");
	var firstName = $("#firstname-signup").val();
	var lastName = $("#lastname-signup").val();
	var email = $("#email-signup").val();
	var password = $("#password-signup").val();
	if (firstName.length == 0) {
//		$("#no-fn-alert-signup").show().delay(3000).fadeOut(10);
//		$("#signup-button").attr("disabled", false);
//		$("#signup-button").css("background-color","#6c757d");
		return;
	}
	if (lastName.length == 0) {
//		$("#no-ln-alert-signup").show().delay(3000).fadeOut(10);
//		$("#signup-button").attr("disabled", false);
//		$("#signup-button").css("background-color","#6c757d");
		return;
	}
	if (email.length == 0) {
//		$("#no-email-alert-signup").show().delay(3000).fadeOut(10);
//		$("#signup-button").attr("disabled", false);
//		$("#signup-button").css("background-color","#6c757d");
		return;
	}
	if (password.length == 0) {
//		$("#no-pass-alert-signup").show().delay(3000).fadeOut(10);
//		$("#signup-button").attr("disabled", false);
//		$("#signup-button").css("background-color","#6c757d");
		return;
	}
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
			$("#signup").modal("hide");
			$("#sent-vemail").modal("show");
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		complete : function() {
			$("#signup-button").attr("disabled", false);
			$("#signup-button").css("background-color","#6c757d");
		}
	});
	var firstName = $("#firstname-signup").val();
	var lastName = $("#lastname-signup").val();
	var email = $("#email-signup").val();
	var password = $("#password-signup").val();
}

// search
function search(){
	var searchString;
	if ($("#search-input").val().length != 0){
		searchString = $("#search-input").val();
	}else{
		return;
	}
	var search = {};
	search["searchString"] = searchString;
	var jsonString = JSON.stringify(search);
//	$.ajax({
//		type : "GET",
//		contentType : "application/json",
//		url : "/search",
//		data : jsonString,
//		cache : false,
//		success : function(data) {
//			console.log("success");
//		},
//		error : function(e) {
//			console.log("ERROR: ", e);
//		}
//	});
}

$(function() {
	$("header").load("/header.html", function() {
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
			console.log($("#search-input").val());
		});
		$("#search-input").keypress(function(e){
			if (e.which == 13)
				console.log($("#search-input").val());
		});
	});
	$("footer").load("/footer.html");

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
	$(".section-carousel-item.details-link, #browse-list > .details-link").width(150);
});