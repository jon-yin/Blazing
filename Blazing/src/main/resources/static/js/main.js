var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
// login
function login(e) {
	e.preventDefault();
	// conditions
	var email = $("#email-login").val();
	var password = $("#password-login").val();
	var fail = false;
	$("#inv-em-login").hide();
	$("#no-em-login").hide();
	$("#no-pw-login").hide();
	$("#inv-pw-login").hide();
	if (email.match(emailRegex) == null && email.length != 0){
		$("#inv-em-login").show();
		fail = true;
	}
	if (email.length == 0){
		$("#no-em-login").show();
		fail = true;
	}
	if (password.length == 0){
		$("#no-pw-login").show();
		fail = true;
	}
	if (fail) return;
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
			if (data == true){
				$("#email-login").val("");
				$("#password-login").val("");
				location.reload();
			}else{
				$("#inv-em-login").show();
				$("#inv-pw-login").show();
			}
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

//sign up
function signup(e) {
	e.preventDefault();
	var firstName = $("#firstname-signup").val();
	var lastName = $("#lastname-signup").val();
	var email = $("#email-signup").val();
	var password = $("#password-signup").val();
	var fail = false;
	$("#no-fn-signup").hide();
	$("#no-ln-signup").hide();
	$("#inv-em-signup").hide();
	$("#used-em-signup").hide();
	$("#no-em-signup").hide();
	$("#no-pw-signup").hide();
	$("#inv-pw-signup").hide();
	if (firstName.length == 0) {
		$("#no-fn-signup").show();
		fail = true;
	}
	if (lastName.length == 0) {
		$("#no-ln-signup").show();
		fail = true;
	}
	if (email.match(emailRegex) == null && email.length != 0){
		$("#inv-em-signup").show();
		fail = true;
	}
	if (email.length == 0){
		$("#no-em-signup").show();
		fail = true;
	}
	if (password.length == 0) {
		$("#no-pw-signup").show();
		fail = true;
	}
	if (fail) {
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
			if (data == true){
				$("#firstname-signup").val("");
				$("#lastname-signup").val("");
				$("#email-signup").val("");
				$("#password-signup").val("");
				$("#signup").modal("hide");
				$("#sent-vemail").modal("show");
			}else{
				$("#used-em-signup").show();
			}
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