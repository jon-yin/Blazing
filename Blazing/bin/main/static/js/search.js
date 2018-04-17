// search script
function search(searchString) {
	var search = {};
	search["searchString"] = searchString;
	var jsonString = JSON.stringify(search);
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/review",
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