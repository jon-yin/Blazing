$(function(){
	var pageSize = properties["mediaPerPage"];
	var count = $("#browse-list .details-link").length / pageSize;
	for (var i = 0; i < count; i++){
		$(".pagination").append('<li class="page-item"><a class="page-link" href="#">'+(i+1)+'</a></li>');
        $(".pagination li").first().find("a").addClass("active")
        showPage = function(page) {
    	    $(".details-link").hide();
    	    $(".details-link").each(function(n) {
    	        if (n >= pageSize * (page - 1) && n < pageSize * page)
    	            $(this).show();
    	    });        
    	}
        
    	showPage(1);

    	$(".pagination li a").click(function() {
    	    $(".pagination li a").removeClass("active");
    	    $(this).addClass("active");
    	    showPage(parseInt($(this).text()));
    	});
	}
});