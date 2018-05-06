const request = require('request-promise');
const cheerio = require('cheerio');
const fs = require('fs');
const es = require('event-stream');


// filename expect movies to be of the format 'm/movie_name'
const FILENAME = 'movieURLs.txt'

var movies = []

const ws = fs.createWriteStream("movie_desc.txt");
const rs = fs.createReadStream(FILENAME, {
		flags: 'r',
		autoclose: true
	})
	.pipe(es.split())
	.pipe(es.mapSync(function(line) {
		if (!line) return;
		rs.pause()

		setTimeout(() => {
			console.log(line)
			scrape(line).then((res) => {
				rs.resume()
			});
		}, 1000)
	}));

function scrape(movie) {
	var movieProps = movie.split('|')
	var movieName = movieProps[0]
	var movieURL = movieProps[1]
	return new Promise(function(resolve, reject) {
		request({
				url: 'https://www.rottentomatoes.com' + movieURL,
				resolveWithFullResponse: true,
				simple: true
			})
			.then((res) => {
				if (res.statusCode === 404) {

				} else {
					console.log(res.statusCode)
					var $ = cheerio.load(res.body)
					var rslt0 = $('#movieSynopsis')
					console.log(rslt0.text())
					ws.write(movieName + '|' + rslt0.text().trim() + '\n')
				}
				resolve()
			}).catch((err) => {
				console.log('skipping')
				resolve()
			});
	})
};