const fs = require('fs');
const path = require('path');
const { MediaScraper } = require('./code/MediaScraper.js');
const { ImageScraper } = require('./code/ImageScraper.js');
const tvShowPosterOptions = require('./options/tvshow-poster-options.js')

try {

  // get url images from rotten tomatoes

  // for each celebrity in file
  // parse their portrait image

  let pinputfile = './output/parsedTVShows/tvshows/ptvshows19.json';
  var pinput = JSON.parse(fs.readFileSync(pinputfile));
  // var pinput = JSON.parse(fs.readFileSync(process.argv[2]));

  let queryStrs = [];
  pinput.forEach((c) => {
    queryStrs.push({ url: c.url });
  });

  // for testing
  // queryStrs = queryStrs.slice(0, 2);

  console.info(`there are ${queryStrs.length} tv shows to query images for`);
  var ms = new MediaScraper(queryStrs, tvShowPosterOptions);
  ms.bigSearch().then((results) => {
    // console.log(results);

    results = results.filter(function (n) { return n != undefined });

    console.info(`got image urls for ${results.length} out of ${queryStrs.length}`);
    // download posters

    let portraitUrls = [];
    results.forEach((e) => {
      portraitUrls.push({
        url: e.url,
        // migth need to check content-type since some images are actually gif
        posterPath: e.url + '_portrait.jpg',
        posterSrc: e.portrait,
      });
    });

    var imageScraper = new ImageScraper();
    let outputPath = path.resolve(__dirname, 'output', 'posters');

    let delay = ms => new Promise(resolve => setTimeout(resolve, ms));
    let delayTime = 2000;

    return portraitUrls.reduce(function (promise, obj) {
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