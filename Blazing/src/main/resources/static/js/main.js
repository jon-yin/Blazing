var account;
$(function(){
	$("header").load("header.html");
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