const fs = require('fs');
const { RTSearchScraper } = require('./code/RTSearchScraper.js');

try {
	let options = {
		apiURL: 'https://www.rottentomatoes.com/api/private/v2.0/search?q=',
		reqDelay: 600,
		jsonKeys: {
			movieKey: 'movies',
			movieCountKey: 'movieCount',
			tvshowsKey: 'tvSeries',
			tvCountKey: 'tvCount',
			castKey: 'castItems'
		}
	};

	// parse titles for academy award winners
	let inFile = './output/winners.json';
	let json = JSON.parse(fs.readFileSync(inFile));

	let winners = [];
	json.awardWinners.forEach(o => {
		winners.push(o.filmName);
	});

	var rt = new RTSearchScraper(winners, options);
	// var rt = new RTSearchScraper(process.argv[2], options);
	rt.search().then(searchResults => {
		let ws = fs.createWriteStream('winnersSearch.json');
		// let ws = fs.createWriteStream(process.argv[3]);
		ws.write(JSON.stringify(searchResults, null, 4));
		ws.close();
	});
} catch (e) {
	console.error(e);
}