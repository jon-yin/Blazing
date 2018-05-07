const fs = require('fs');
const { MediaScraper } = require('./code/MediaScraper.js');
const seasonOptions = require('./options/season-options');

const inputfile = './output/parsedTVshows/ptvshows00.json';

try {
  var json = JSON.parse(fs.readFileSync(process.argv[2]));
  // var json = JSON.parse(fs.readFileSync(inputfile));

  /// look up the number of seasons

  // read a tvshow object.
  // create request with tvshow url as 
  // run request. extract season urls
  // copy the contents of the tvSeries object 
  // and add the number of seasons, and an array of season urls for each show

  // filter out season urls. we want show urls

  let seasonLinks = [];

  json.forEach(tvShow => {
    tvShow.seasons.forEach(seasonUrl => {
      if (seasonUrl != null) { seasonLinks.push({ url: seasonUrl }); }
    });
  });

  // var ms = new MediaScraper(seasonLinks.slice(4,10), seasonOptions);
  var ms = new MediaScraper(seasonLinks, seasonOptions);

  ms.bigSearch()
    .then(result => {

      let checkProperties = function (obj) {
        for (var key in obj) {
          if (seasonOptions.notRequired.includes(key)) {
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