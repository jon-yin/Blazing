const request = require('request-promise');
const cheerio = require('cheerio');
const fs = require('fs');
const es = require('event-stream');

const URL_RT_SEARCH = 'https://www.rottentomatoes.com/api/private/v2.0/'
const WAIT_TIME = 500
const MOVIES_KEY = 'movies',
	MOVIE_COUNT_KEY = 'movieCount'
const TVSHOWS_KEY = 'tvSeries',
	TV_COUNT_KEY = 'tvCount'
const CAST_KEY = 'castItems'

const BASE_PROPS = ['name', 'url']
const MOVIE_PROPS = BASE_PROPS + ['year', 'meterScore']
const TV_PROPS = BASE_PROPS + ['startYear', 'endYear', 'meterScore']

// use line in file as seed
// do a search via the api
// parse the json object
// movieCount and tvCount
// save the movies and tv show objects

var jsonObject = {}
jsonObject[MOVIES_KEY] = []
jsonObject[TVSHOWS_KEY] = []
jsonObject[CAST_KEY] = []

var urlLines = []

// return content for matched query
function do_get(mediaName) {

	var query = {
		url: URL_RT_SEARCH + 'search?q=' + mediaName,
		resolveWithFullResponse: true,
		simple: true
	};

	return request(query)
		.then((res) => {
			// console.log(res.statusCode)
			var rslt = JSON.parse(res.body, null, 2)
				// console.log(rslt.movieCount)

			if (rslt[MOVIE_COUNT_KEY]) {
				rslt[MOVIES_KEY].forEach((movie) => {
					getMediaData(movie, MOVIES_KEY)
				});
			}

			if (rslt[TV_COUNT_KEY]) {
				rslt[TVSHOWS_KEY].forEach((tvshow) => {
					getMediaData(tvshow, TVSHOWS_KEY)
				});
			}
			// console.log(JSON.stringify(jsonObject, null, 2))
		})
}

function do_url_get(mediaName) {

	var query = {
		url: URL_RT_SEARCH + 'search?q=' + mediaName,
		resolveWithFullResponse: true,
		simple: true
	};

	request(query)
		.then((res) => {
			// console.log(res.statusCode)
			var rslt = JSON.parse(res.body, null, 2)
				// console.log(rslt.movieCount)

			if (rslt[MOVIE_COUNT_KEY]) {
				rslt[MOVIES_KEY].forEach((movie) => {
					urlLines.push({
						name: movie.name,
						url: movie.url
					})
				});
			}

			if (rslt[TV_COUNT_KEY]) {
				rslt[TVSHOWS_KEY].forEach((tvshow) => {
					urlLines.push({
						name: tvshow.title,
						url: tvshow.url
					})
				});
			}
			// console.log(JSON.stringify(jsonObject, null, 2))
		});

}

function getMediaData(media, mediaType) {
	var mediaEntry = {}
	var mediaProps = []

	switch (mediaType) {
		case MOVIES_KEY:
			mediaEntry = Object.assign({
				name: media.name || media.title,
				url: media.url,
				year: media.year,
				meterClass: (media.meterClass && media.meterClass !== 'N/A') ?
					media.meterScore : 'N/A'
			}, mediaEntry)

			// add celebrities (only available in movies)
			media[CAST_KEY].forEach((celeb) => {
				jsonObject[CAST_KEY].push(celeb)
			})

			jsonObject[MOVIES_KEY].push(mediaEntry)
			break;
		case TVSHOWS_KEY:

			mediaEntry = Object.assign({
				name: media.name || media.title,
				url: media.url,
				startYear: (media.startYear !== 0) ? media.startYear : 0,
				endYear: (media.endYear !== 0) ? media.endYear : 0,
				meterClass: (media.meterClass && media.meterClass !== 'N/A') ?
					media.meterScore : 'N/A'
			}, mediaEntry)

			jsonObject[TVSHOWS_KEY].push(mediaEntry)
			break;
		default:
			console.error('bad media entry')
	}
	// if movie: also get cast data
}


function parseMovies() {

	return new Promise(function(resolve, reject) {

		const rs = fs.createReadStream('movies-small.txt', {
				flags: 'r',
				autoclose: true,
			})
			.pipe(es.split())
			.pipe(es.mapSync(function(line) {
					if (!line) {
						resolve();
					} // EOF
					rs.pause();

					console.log('seed: ' + line)

					// query RT with movie
					setTimeout(() => {
						do_url_get(line);
						// do_get(line).then(function(rsltJson) {

						// 	writeMovieURLs(lines)
						// 		// writeBIGjson(jsonObject)
						// 		// merge results of search with old file
						// 		// appendToJSON(mediaJson.txt, rsltJson)

						rs.resume()
							// });
					}, WAIT_TIME)
				})
				.on('error', function(err) {
					console.log('Error while reading file.', err);
				})
				.on('end', function() {
					console.log('Read entire file.')
				})
			);
	})
}

parseMovies().then(function() {
	if (urlLines)
		writeMovieURLs()
})

// finally write 
function writeMovieURLs() {
	const ws = fs.createWriteStream("movieURLs.txt");
	urlLines.forEach(function(urlLine) {
			console.log('writing: ' + urlLine.name + '|' + urlLine.url)
			if (urlLine.name && urlLine.url) {
				ws.write(urlLine.name + '|' + urlLine.url + '\n')
			}
		})
		// done
}

function writeBIGjson(jsonObj) {
	const ws = fs.createWriteStream("mediaJson.json")
	ws.on('finish', () => {
		console.log('finished writing')
	})
	ws.write('hell' + '\n')
		// ws.write(JSON.stringify(jsonObj))
}
// do this in the future. Will implement this as a duplex stream
function appendToJSON(originalJsonObject, newJsonObject) {

	const ws = fs.createWriteStream("mediaJson.txt");

}