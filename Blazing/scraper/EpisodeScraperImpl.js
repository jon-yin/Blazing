const fs = require('fs');
const { MediaScraper } = require('./code/MediaScraper.js');
const episodeOptions = require('./options/episode-options');

const inputfile = './output/parsedTVshows/parsedSeasons/pseasons00.json';

try {
  var json = JSON.parse(fs.readFileSync(process.argv[2]));
  // var json = JSON.parse(fs.readFileSync(inputfile));

  let episodeLinks = [];

  json.forEach(season => {
    season.episodesUrl.forEach(episodeUrl => {
      if (episodeUrl != null) { episodeLinks.push({ url: episodeUrl }); }
    });
  });

  var ms = new MediaScraper(episodeLinks, episodeOptions);
  // var ms = new MediaScraper(episodeLinks.slice(99, 102), episodeOptions);

  ms.bigSearch()
    .then(result => {

      let checkProperties = function (obj) {
        for (var key in obj) {
          if (episodeOptions.notRequired.includes(key)) {
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