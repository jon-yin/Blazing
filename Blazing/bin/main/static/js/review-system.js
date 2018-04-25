// review system
function toggleWTS() {
	$("#not-interested").attr("disabled", true);
	$("#not-interested").css("background-color","#aaaaaa");
	$("#not-interested").css('border-color','#aaaaaa');
	$("#want-to-see").attr("disabled", true);
	$("#want-to-see").css("background-color","#aaaaaa");
	$("#want-to-see").css('border-color','#aaaaaa');
	if($("#not-interested").hasClass("active")){
		$("#not-interested").removeClass("active");
		$("#not-interested .button-text").replaceWith('<span class="button-text"><i class="fa fa-minus"></i> Not Interested</span>');
	}
	if ($("#want-to-see").hasClass("active")){
		$("#want-to-see").css('background-color','#343a40');
		$("#want-to-see").css('border-color','#343a40');
		$("#want-to-see .button-text").replaceWith('<span class="button-text"><i class="fa fa-plus"></i> Want To See</span>');
	}else{
		$("#want-to-see").css('background-color','#28a745');
		$("#want-to-see").css('border-color','#28a745');
		$("#want-to-see .button-text").replaceWith('<span class="button-text"><i class="fa fa-check"></i> Will See!</span>');
	}
	$("#want-to-see").attr("disabled",false);
	$("#not-interested").attr("disabled",false);
	$("#not-interested").css('background-color','#343a40');
	$("#not-interested").css('border-color','#343a40');
}

function toggleNI() {
	$("#not-interested").attr("disabled", true);
	$("#not-interested").css("background-color","#aaaaaa");
	$("#want-to-see").attr("disabled", true);
	$("#want-to-see").css("background-color","#aaaaaa");
	if($("#want-to-see").hasClass("active")){
		$("#want-to-see").removeClass("active");
		$("#want-to-see .button-text").replaceWith('<span class="button-text"><i class="fa fa-plus"></i> Want To See</span>');
	}
	if ($("#not-interested").hasClass("active")){
		$("#not-interested").css('background-color','#343a40');
		$("#not-interested").css('border-color','#343a40');
		$("#not-interested .button-text").replaceWith('<span class="button-text"><i class="fa fa-minus"></i> Not Interested</span>');
	}else{
		$("#not-interested").css('background-color','#dc3545');
		$("#not-interested").css('border-color','#dc3545');
		$("#not-interested .button-text").replaceWith('<span class="button-text"><i class="fa fa-times"></i> Pass...</span>');
	}
	$("#want-to-see").attr("disabled",false);
	$("#not-interested").attr("disabled",false);
	$("#want-to-see").css('background-color','#343a40');
	$("#want-to-see").css('border-color','#343a40');
}

function review(score, body){
	var reviewDetails = {};
	reviewDetails["score"] = score;
	reviewDetails["body"] = body;
	var jsonString = JSON.stringify(reviewDetails);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : location.href+"/submitreview",
		data : jsonString,
		cache : false,
		success : function(data) {
			console.log("success");
			location.reload();
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
}

$(function(){
	$("#want-to-see").on("click",toggleWTS);
	$("#not-interested").on("click",toggleNI);
	$("#submit-rating").on("click",function(e){
		e.preventDefault();
		var score = $("#rating-number").val();
		if (score == "0"){
			$("#submit-fail").show();
		}else{
			review(score,$("#rvw-desc").val());
		}
	});
});