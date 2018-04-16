// review system
function toggleWTS() {

}

function toggleNI() {

}

function review(rating, paragraph, media){

}

$(function(){
	// you can add bool values
	$("#want-to-see").on("click",function(){
		if($("#not-interested").hasClass("active")){
			$("#not-interested").removeClass("active");
			$("#not-interested").css('background-color','#343a40');
			$("#not-interested").css('border-color','#343a40');
			$("#not-interested .button-text").replaceWith('<span class="button-text"><i class="fa fa-minus"></i> Not Interested</span>');
		}
		if ($(this).hasClass("active")){
			$(this).css('background-color','#343a40');
			$(this).css('border-color','#343a40');
			$("#want-to-see .button-text").replaceWith('<span class="button-text"><i class="fa fa-plus"></i> Want To See</span>');
		}else{
			$(this).css('background-color','#28a745');
			$(this).css('border-color','#28a745');
			$("#want-to-see .button-text").replaceWith('<span class="button-text"><i class="fa fa-check"></i> Will See!</span>');
		}
	});
	$("#not-interested").on("click",function(){
		if($("#want-to-see").hasClass("active")){
			$("#want-to-see").removeClass("active");
			$("#want-to-see").css('background-color','#343a40');
			$("#want-to-see").css('border-color','#343a40');
			$("#want-to-see .button-text").replaceWith('<span class="button-text"><i class="fa fa-plus"></i> Want To See</span>');
		}
		if ($(this).hasClass("active")){
			$(this).css('background-color','#343a40');
			$(this).css('border-color','#343a40');
			$("#not-interested .button-text").replaceWith('<span class="button-text"><i class="fa fa-minus"></i> Not Interested</span>');
		}else{
			$(this).css('background-color','#dc3545');
			$(this).css('border-color','#dc3545');
			$("#not-interested .button-text").replaceWith('<span class="button-text"><i class="fa fa-times"></i> Pass...</span>');
		}
	});
	$("#submit-rating").on("click",function(e){
		e.preventDefault();
		var rating = $("#rating-number").val();
		if (rating == "0"){
			$("#submit-fail").show();
		}else{
			location.reload();
		}
	});
});