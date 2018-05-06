const fs = require('fs');
const { RTSearchScraper } = require('./RTSearchScraper.js');

try {
	let options = {
		apiURL: 'https://www.rottentomatoes.com/api/private/v2.0/search?q=',
		reqDelay: 2000,
		jsonKeys: {
			movieKey: 'movies',
			movieCountKey: 'movieCount',
			tvshowsKey: 'tvSeries',
			tvCountKey: 'tvCount',
			castKey: 'castItems'
		}
	};
	var rt = new RTSearchScraper(process.argv[2], options);
	rt.search().then(searchResults => {
		let ws = fs.createWriteStream(process.argv[3]);
		ws.write(JSON.stringify(searchResults));
		ws.close();
	});
} catch (e) {
	console.error(e);
}