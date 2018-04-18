function getDataFromQueryString(){
	var string = location.search.substring(1);
	var keyValuePairs = string.split("&");
	var test = [];
	for (var i = 0; i < keyValuePairs.length; i++){
		keyValuePairs[i] = keyValuePairs[i].split("=");
		test.push({key:keyValuePairs[i][0],value:decodeURIComponent(keyValuePairs[i][1])});
		// keyValuePairs[i][1] = keyValuePairs[i][1].split(";");
	}
	var browseData = test.reduce(function(map, obj) {
	    map[obj.key] = obj.value;
	    return map;
	}, {});
	return browseData;
}
