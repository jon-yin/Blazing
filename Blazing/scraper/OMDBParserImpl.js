const fs = require('fs');
const request = require('request-promise')
const { OMDBParser } = require('./OMDBParser.js');

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

  let pmovieFile = './output/parsedMovies/parsedMovies1950Up/pmovies00.json';
  var pmovies = JSON.parse(fs.readFileSync(pmovieFile));

  let queryStrs = [];
  pmovies.forEach((m) => {
    let q = APIURL + m.title + YEAR + m.premiered.match(/\d\d\d\d$/) + APIKEY;
    console.log(q);
    queryStrs.push(q);
  });

  var omdb = new OMDBParser(queryStrs.slice(30, 50), options);

  omdb.search().then((res) => {
    // should be an array of url + boxOffice objects
    console.log(res);
  });

  // let json = [];
  // request({ url: 'http://www.omdbapi.com/?t=scary movie 4' + APIKEY }).then(res => {
  //   let results = JSON.parse(res, null, 4);

  //   // pmovies.forEach(obj => {
  //   //   json.push(Object.assign({}, obj, { boxOffice: obj.BoxOffice }));
  //   // });

  //   console.log({ boxOffice: results.BoxOffice });
  // });

}
catch (e) {
  console.error(e);
}