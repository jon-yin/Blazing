const fs = require('fs');
const path = require('path');
const { OMDBParser } = require('./code/OMDBParser.js');
const { ImageScraper } = require('./code/ImageScraper.js');

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

  let pmovieFile = './output/awardWinners/pMoviesWinnersSearch.json';
  var pmovies = JSON.parse(fs.readFileSync(pmovieFile));

  let queryStrs = [];
  pmovies.forEach((m) => {
    // for movies
    // let premiereYear = m.premiered.match(/\d\d\d\d$/);
    // for tvshows
    let premiereYear = m.premiere.match(/\d\d\d\d$/);
    let title = m.title.replace(/\(.*\)/g, '');
    let q = APIURL + title + YEAR + premiereYear[0] + APIKEY;
    queryStrs.push({ url: m.url, query: q });
  });

  queryStrs = queryStrs.slice(0, 10);

  console.info(`there are ${queryStrs.length} to query the OMDB api`);
  var omdb = new OMDBParser(queryStrs, options);
  omdb.search().then((results) => {

    results = results.filter(function (n) { return n != undefined });

    console.info(`got image urls for ${results.length} out of ${queryStrs.length}`);
    // download posters
    var imageScraper = new ImageScraper(results);
    // let outputPath = path.resolve(__dirname, 'output', 'awardWinners', 'posters');
    let outputPath = path.resolve(__dirname, 'output', 'posters');

    let delay = ms => new Promise(resolve => setTimeout(resolve, ms));
    let delayTime = 2000;

    return results.reduce(function (promise, obj) {
      return promise.then(function () {
        if (obj) {
          return Promise.all([
            delay(delayTime),
            imageScraper.fetchImage(obj, outputPath)]);
        }
        else {
          return Promise.resolve();
        }
      })
    }, Promise.resolve()).then(() => {
      console.log('all images saved at ', outputPath);
    });
  });
}
catch (e) {
  console.error(e);
}