$(function() {
	// load header and scripts for header
	$("header").load("header.html", function() {
		//login and signup
		$.getScript("js/login-signup.js", function() {
			var isLoggedIn = checkLogin();
			if (isLoggedIn) {
				$("#logged-in").show();
				$("#not-logged-in").hide();
			}else{
				$("#not-logged-in").show();
				$("#logged-in").hide();
			}
			$("#login-button").on("click",function(e) {
				e.preventDefault();
				var email = $("#email-login").val();
				var password = $("#password-login").val();
				var status = login(email, password);
				if (status == 1) {
					$("#inval-user-alert-login").show();
					$("#inval-pass-alert-login").hide();
					$("#no-pass-alert-login").hide();
				}else if (status == 2) {
					$("#inval-pass-alert-login").show();
					$("#inval-user-alert-login").hide();
					$("#no-pass-alert-login").hide();
				}
			});
			$("#logout-button").on("click",function() {
				logout();
			});
			$("#signup-button").on("click",function(e) {
				e.preventDefault();
				var firstName = $("#firstname-signup").val();
				var lastName = $("#lastname-signup").val();
				var email = $("#email-signup").val();
				var password = $("#password-signup").val();
				var status = signup(firstName, lastName, email, password);
			});
		});
		//search
		$.getScript("js/search.js", function() {
			$("#search-button").on("click",function(){
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