var properties;
// login
function login(e) {
	e.preventDefault();
	// conditions
	var email = $("#email-login").val();
	var password = $("#password-login").val();
	var emailRegex = /\w*@[a-z]+(.[a-z]+)+/;
	if (email.match(emailRegex) == null){
		console.log("fail regex");
		return;
	}
	if (email.length == 0){
		return;
	}
	if (password.length == 0){
		return;
	}
	$("#login-button").attr("disabled", true);
	$("#login-button").css("background-color","#aaaaaa");
	var loginCredentials = {};
	loginCredentials["email"] = email;
	loginCredentials["password"] = password;
	var jsonString = JSON.stringify(loginCredentials);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/login",
		data : jsonString,
		success : function(data) {
			console.log("success");
			$("#email-login").val("");
			$("#password-login").val("");
			$("#login").modal("hide");
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
	//remove user from session
}

//sign up
function signup(e) {
	e.preventDefault();
	// temp - change to display loading
	var firstName = $("#firstname-signup").val();
	var lastName = $("#lastname-signup").val();
	var email = $("#email-signup").val();
	var password = $("#password-signup").val();
	if (firstName.length == 0) {
//		$("#no-fn-alert-signup").show().delay(3000).fadeOut(10);
		return;
	}
	if (lastName.length == 0) {
//		$("#no-ln-alert-signup").show().delay(3000).fadeOut(10);
		return;
	}
	if (email.length == 0) {
//		$("#no-email-alert-signup").show().delay(3000).fadeOut(10);
		return;
	}
	if (password.length == 0) {
//		$("#no-pass-alert-signup").show().delay(3000).fadeOut(10);
		return;
	}
	$("#signup-button").attr("disabled", true);
	$("#signup-button").css("background-color","#aaaaaa");
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
		success : function(data) {
			console.log("success");
			$("#firstname-signup").val("");
			$("#lastname-signup").val("");
			$("#email-signup").val("");
			$("#password-signup").val("");
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
}

$("sent-vemail").on("hide.bs.modal",function(){
	$("header").load("header");
});

// search
function search(){
	var searchString;
	if ($("#search-input").val().length != 0){
		searchString = $("#search-input").val();
	}else{
		return;
	}
	location.href = "/search?q="+encodeURIComponent($("#search-input").val());
}

$(function() {
	$("#login-button").on("click", login);
	$("#logout-button").on("click", logout);
	$("#signup-button").on("click", signup);
	$("#search-button").on("click", search);
	$("#search-input").keypress(function(e){
		if (e.which == 13)
			search();
	});
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