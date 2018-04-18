$(function(){
	var pageSize = properties["mediaPerPage"];
	var count = $("#browse-list .details-link").length / pageSize;
	console.log(count);
});