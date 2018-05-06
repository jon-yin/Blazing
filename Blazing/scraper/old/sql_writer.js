const fs = require('fs')
const es = require('event-stream');

const ws = fs.createWriteStream("sql_outfile.sql");
const rs = fs.createReadStream('movie_desc.txt', {
		flags: 'r',
		autoclose: true
	})
	.pipe(es.split())
	.pipe(es.mapSync(function(line) {
			if (!line) return; // EOF
			rs.pause();

			// query RT with movie
			setTimeout(function() {
				createSQLInsertStatement(line).then(function(result) {
					// console.log('from: ' + result)
					rs.resume()
				})
			}, 500)

		})
		.on('error', function(err) {
			console.log('Error while reading file.', err);
		})
		.on('end', function() {
			console.log('Read entire file.')
		})
	);

var GLOBAL_COUNT = 2;

function createSQLInsertStatement(line) {

	var movieProps = line.split('|')
		// console.log('movie props: ' + movieProps)
	var movieName = movieProps[0]
	var movieDesc = movieProps[1]

	movieName = movieName.replace(/\'/g, "''")
	movieName = movieName.replace(/\"/g, '')

	movieDesc = movieDesc.replace(/\'/g, "''")
	movieDesc = movieDesc.replace(/\"/g, '')

	// var sqlStr =
	// 	`insert into MOVIE(id, title, description, videos, images, cast, reviews, quotes) values(, "${movieName}", "${movieDesc}", "poster", "airtimes", "audienceScore", "blazingScore", "videos", "images", "cast", "reviews", "quotes");`

	var sqlStr =
		`insert into MOVIE(id, title, description, audience_score, blazing_score, box_office, run_time) values(${GLOBAL_COUNT}, '${movieName}', '${movieDesc}', 0,0,0,0);`

	GLOBAL_COUNT++;

	return new Promise(function(res, rej) {
		if (movieName && movieDesc) {
			ws.write(sqlStr + '\n\n')
				console.log('writing query for ' + movieName)
		}
		res(sqlStr)
	})
}