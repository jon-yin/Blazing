function addActor(e){
	e.preventDefault();
	var actorID = $("#add-actor-id").val();
	var actorRole = $("#add-actor-role").val();
	var actorString = actorID + ":" + actorRole+"\n";
	$("#added-cast-box").append(actorString);
	$("#add-actor-id").val("");
	$("#add-actor-role").val("");
}

function addMovie(e){
	e.preventDefault();
	$("#add-movie-button").attr("disabled", true);
	$("#add-movie-button").css("background-color","aaaaaa");
	var movieTitle = $("#add-movie-title").val();
	var movieDesc = $("#add-movie-desc").val();
	var movieCast = $("#added-cast-box").val();
	var runTime = $("#add-runtime").val();
	var releaseDate = $("#add-year").val()+$("#add-month").val()+$("#add-day").val();
	var boxOffice = $("#add-boxoffice").val();
	var movieVar = {};
	movieVar["title"] = movieTitle;
	movieVar["description"] = movieDesc;
	movieVar["cast"] = movieCast;
	movieVar["runTime"] = runTime;
	movieVar["boxOffice"] = boxOffice;
	movieVar["airtimes"] = [releaseDate];
	var jsonString = JSON.stringify(movieVar);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : window.location.hostname+"/upload_movie",
		data : jsonString,
		cache : false,
		success : function(data) {
			console.log("success");
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		complete : function() {
			$("#add-movie-button").attr("disabled", false);
			$("#add-movie-button").css("background-color","#6c757d");	
		}
	});
}

$(function(){
	$("#add-actor-button").on("click",addActor);
	$("#add-movie-button").on("click",addMovie);
});