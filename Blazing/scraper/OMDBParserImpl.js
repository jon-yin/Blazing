const fs = require('fs');
const request = require('request-promise')
const { OMDBParser } = require('./code/OMDBParser.js');

// let url = 'http://www.omdbapi.com/?i=tt3896198&apikey=cea2a16a';

// for each movie object, add the box office prop to the object
// adds the boxOffice field to an existing movie object
// returns json of all the new movie objects

try {


  let APIURL = 'http://www.omdbapi.com/?t=';
  let YEAR = '&y=';
  let APIKEY = '&apikey=cea2a16a';

  const options = {
    reqDelay: 1000,
    jsonKeys: {
      boxOffice: 'BoxOffice',
      poster: 'Poster'
    }
  }

  let pmovieFile = './output/parsedMovies/parsedMovies1950Up/pmovies19.json';
  var pmovies = JSON.parse(fs.readFileSync(pmovieFile));

  let queryStrs = [];
  pmovies.forEach((m) => {
    let q = APIURL + m.title + YEAR + m.premiered.match(/\d\d\d\d$/) + APIKEY;
    queryStrs.push({ url: m.url, query: q });
  });

  var omdb = new OMDBParser(queryStrs.slice(30, 32), options);
  omdb.search().then((results) => {
    console.log(results);

  });

}
catch (e) {
  console.error(e);
}