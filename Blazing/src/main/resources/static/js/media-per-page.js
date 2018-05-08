var pageSize;
var count;

function showPage(page){
	$(".pagination li").hide();
    $(".details-link").hide();
    $(".details-link").each(function(n) {
    	if (n >= pageSize * (page - 1) && n < pageSize * page)
    		$(this).show();
    });
    for (i = page-1; i >= page-5 && i > 0; i--){
    	var pageID = "#page-"+i;
    	$(pageID).show();
    }
    var currId = "#page-"+page;
    $(currId).show();
    for (i = page+1; i <= page+5 && i <= count+1; i++){
    	var pageID = "#page-"+i;
    	$(pageID).show();
    }
    if (page-5 > 0){
    	$("#page-1").show();
    	$("#page-ell-prev").show();
    }
    if (page+5 < count){
    	$("#page-"+Math.floor(count+1)).show();
    	$("#page-ell-next").show();
    }
}

$(function(){
	pageSize = properties["mediaPerPage"];
	count = $(".results-list .details-link").length / pageSize;
	console.log(count);
	for (var i = 0; i < count; i++){
		if (i == 1) $(".pagination").append('<li id="page-ell-prev" class="page-item"><a class="page-link">...</a></li>');
		$(".pagination").append('<li id="page-'+(i+1)+'" class="page-item"><a class="page-link" href="#">'+(i+1)+'</a></li>');
		if (i == (Math.floor(count-1))) $(".pagination").append('<li id="page-ell-next" class="page-item"><a class="page-link">...</a></li>');
	}
		$(".pagination li").first().addClass("active");
		showPage(1);
		$(".pagination li").on("click",function(e) {
			e.preventDefault()
			if ($(this).attr("id") != "page-ell-prev" && $(this).attr("id") != "page-ell-next"){
				$(".pagination li").removeClass("active");
				$(this).addClass("active");
				showPage(parseInt($(this).text()));
			}
		});
});