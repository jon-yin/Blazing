const fs = require('fs');
const { MediaScraper } = require('./code/MediaScraper.js');
const tvShowOptions = require('./options/tvshow-options');

const inputfile = './searchResults/done/movies00.json';

try {

  // get all the season urls from the tvshows object

  var json = JSON.parse(fs.readFileSync(process.argv[2]));
  console.log(`there are ${json.tvSeries.length} TV Shows`);
  // var json = JSON.parse(fs.readFileSync(inputfile));

  // filter out season urls. we want show urls
  let tvShows = [];
  json.tvSeries.forEach(show => {
    if (show.startYear >= 1950) {
      let v = show.url.match(/\/s\d\d$/);
      if (v === null) {
        tvShows.push({ url: show.url });
      }
    }
  });

  console.log(`there are ${tvShows.length} TV Shows after year 1950`);


  var ms = new MediaScraper(tvShows, tvShowOptions);

  ms.bigSearch()
    .then(result => {

      let checkProperties = function (obj) {
        for (var key in obj) {

          if (tvShowOptions.notRequired.includes(key)) {
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

      // if any property is empty, discard object
      let cleanedResults = [];
      for (let i = 0; i < result.length; i++) {
        if (checkProperties(result[i])) {
          cleanedResults.push(result[i]);
        }
      }

      // if result is clean start getting large media like images, videos

      console.log(`Parsed ${result.length} urls, after clean up, got ${cleanedResults.length} results`)

      let f = JSON.stringify(cleanedResults, null, 4);
      fs.writeFileSync(process.argv[3], f);
      // fs.writeFileSync('seasonout.json', f);
    });

}
catch (e) {
  console.error(e);
} 