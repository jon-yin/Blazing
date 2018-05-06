/*
	Get the top n movies for a range of years
    : is replaced with _
    , is replaced with _
    If title starts with "The", it is omitted in the url

    USAGE: node scraper.js YEAR_START [YEAR_END] [OUTPUT_FILE]

*/

const request = require('request-promise');
const cheerio = require('cheerio');
const fs = require('fs');

const MAX_CONSECUTIVE_ERR_REQ = 10;

var year = process.argv[2];
var endYear = process.argv[3] || year;
var outfile = process.argv[4];
var canWrite = true;

if (process.argv.length < 2){
    // print usage
    process.exit();
}

if (endYear < year) {
    console.error('End Year %d cannot be before start Year %d', year, endYear);
    process.exit();
}

const ws = fs.createWriteStream(outfile || "outfile.txt");
ws.on('drain', () => { canWrite = true; })
    .on('error', () => { console.error('error writing'); })
    .on('close', () => { console.info('writer closed'); })
    .on('finish', () => { console.info('finished scraping'); });

process.on('SIGINT', function() {
	console.log('Closing...');
	ws.end();
	ws.once('close', process.exit);
});

function scrape(count) {
    if (year <= endYear && canWrite && count <= MAX_CONSECUTIVE_ERR_REQ) {
        request({
            url: 'http://www.filmsite.org/' + year + '.html',
            resolveWithFullResponse: true,
            simple: true
        })
        .then( (res) => {
            console.info('Year: %d, Response: %s', year, res.statusCode);
            // canWrite = ws.write('Year: '+ year + '\n' + parseMovies(res.body) + '\n');
            canWrite = ws.write(parseMovies(res.body) + '\n');
            // console.info('Consecutive error request: %d', count);
            year++;
            scrape(1);
        })
        .catch( (err) => {
            if (err.statusCode === 404){
                console.error('Year: %d, Response: %s', year, err.statusCode);
            }
            year++;
            console.info('Consecutive error request: %d\t limit: %d', count, MAX_CONSECUTIVE_ERR_REQ);
            if (MAX_CONSECUTIVE_ERR_REQ === count) {
                console.info('Max consecutive errors reached. Quitting...');
                ws.end();
                canWrite = false;
            }
            else {
                scrape(++count);
            }
        });
    }
    else if (!canWrite) {
        // wait for write buffer to free up
        ws.once('drain', scrape(1));
    }
    else {
        ws.end();
    }
};

function parseMovies(htmlString){
    var $ = cheerio.load(htmlString);
    var rslt0 = $('#mainBodyWrapper > blockquote > table > tbody > tr').not('tr:nth-child(1)');

    var rslt01 = rslt0.find('b');
    var rslt02 = rslt0.find('strong');

    var rslt001 = [];
    var rslt002 = [];

    rslt01.each ( function (i, el) {
        var sop = $(this).text();
        sop = cleanMovieTitle(sop);
        if (sop.match(/^\S+/)){
            rslt001.push(sop);
        }
    });

    rslt02.each ( function (i, el) {
        var sop = $(this).text();
        sop = cleanMovieTitle(sop);
        if (sop.match(/^\S+/)){
            rslt002.push(sop);
        }
    });

    return rslt001.concat(rslt002).join('\n');
}

function cleanMovieTitle(movie){
    movie = movie.replace(/\r?\n|\r/g, '');
    movie = movie.replace(/^,/g, '');
    movie = movie.replace(/\s+/g, ' ');
    // clear movie year and other misc info
    movie = movie.replace(/\(.*/, "");
    // remove trailing spaces
    movie = movie.replace(/\s+$/, "");

    return movie;
}

scrape(1);

module.exports = scrape;
