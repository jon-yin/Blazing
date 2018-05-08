function review(score, body){
	var reviewDetails = {};
	reviewDetails["score"] = score;
	reviewDetails["body"] = body;
	if(!$("#is-blazing").length)
		reviewDetails["isBlazing"] = document.getElementById('is-blazing').checked;
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
	$("#submit-rating").on("click",function(e){
		e.preventDefault();
		var score = $("#rating-number").val();
		if (score == "0"){
			$("#submit-fail").show();
		}else{
			review(score, $("#rvw-desc").val());
		}
	});
});