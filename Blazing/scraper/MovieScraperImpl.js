const fs = require('fs');
const { MediaScraper } = require('./MediaScraper.js');
const movieOptions = require('./options/movie-options');

const inputfile = './searchResults/done/movies00.json';

try {
  // var json = JSON.parse(fs.readFileSync(process.argv[2]));
  var json = JSON.parse(fs.readFileSync(inputfile));
  var ms = new MediaScraper(json.movies.slice(0,40), movieOptions);

  // iterate over all entries, scrape info and return
  ms.bigSearch()
    .then(result => {

      let checkProperties = function (obj) {
        for (var key in obj) {

          if (movieOptions.notRequired.includes(key)) {
            continue;
          }

          if (Array.isArray(obj[key]) && obj[key].length < 1) {
            return false;
          }
          if (obj[key] === "") {
            return false;
          }
        }
        return true;
      };

      let cleanedResults = [];
      for (let i = 0; i < result.length; i++) {
        if (checkProperties(result[i])) {
          cleanedResults.push(result[i]);
        }
      }

      // if result is clean start getting large media like images, videos

      console.log(`Parsed ${result.length} urls, after clean up, got ${cleanedResults.length} results`)

      let f = JSON.stringify(cleanedResults, null, 4);
      // fs.writeFileSync(process.argv[3], f);
      fs.writeFileSync('pmoviesgood.json', f);
    });
}
catch (e) {
  console.error(e);
}

//   // MediaScraper.getMovieInfo(movieCriticsUrl, options);
//   // const ws = fs.createWriteStream('img.jpg')
//   // request({
//   //   url: 'https://resizing.flixster.com/QPYc2XjxYs9_kvvTK6ApU1e6pL8=/206x305/v1.bTsxMjcxNDA5NDtqOzE3Njk3OzEyMDA7NDA1MDs2MDAw',
//   //   resolveWithFullResponse: true,
//   //   encoding: null
//   // }).then((res) => {