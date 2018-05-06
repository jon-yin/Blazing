const fs = require('fs');
const { MediaScraper } = require('./MediaScraper.js');
const criticReviewOptions = require('./input/critic-options');

// const inputfile = './output/parsedMovies/pmovies00.json';

// parses critic reviews for a movie url

try {
  var json = JSON.parse(fs.readFileSync(process.argv[2]));
  // var json = JSON.parse(fs.readFileSync(inputfile));

  // filter out season urls. we want show urls
  let criticReview = [];
  json.forEach(review => {
    criticReview.push({ url: review.url + criticReviewOptions.appendToUrlEnd });
  });

  var ms = new MediaScraper(criticReview, criticReviewOptions);

  ms.bigSearch()
    .then(result => {

      let checkProperties = function (obj) {
        for (var key in obj) {
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
          // cleanedResults.push({ url: result[i] });
          cleanedResults.push(result[i]);
        }
      }

      // if result is clean start getting large media like images, videos

      console.log(`Parsed ${result.length} urls, after clean up, got ${cleanedResults.length} results`)

      let f = JSON.stringify(cleanedResults, null, 4);
      fs.writeFileSync(process.argv[3], f);
      // fs.writeFileSync('jkjkjkjk.json', f);
    });

}
catch (e) {
  console.error(e);
}