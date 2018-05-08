/*
    program takes in a json of media urls
    for each media url, navigate to the media page
    and collect media information, media reviews, etc.
    returns a json of all the parsed data
*/

const request = require('request-promise');
const fs = require('fs');
const path = require('path');

module.exports.ImageScraper = class ImageScraper {
  // does one request per item
  fetchImage(jsonObj, outputPath) {

    return new Promise((resolved, rejected) => {
      if (jsonObj != null) {
        request({
          // maybe use content-type, but images should already be jpeg
          url: jsonObj.posterSrc,
          encoding: null // null defaults to binary 
        }).then((res) => {
          // console.info(`got image for ${jsonObj.url}`);
          let p = path.join(outputPath, jsonObj.posterPath);
          // fs.writeFileSync(p, res);

          fs.writeFile(p, res, null, function () {
            console.info(`wrote image ${jsonObj.posterPath}`);
            resolved();
          });

          // resolved();
        }).catch(err => {
          // continue... ignore the failed request
          console.error(`image request for ${jsonObj.url} failed. skipping...`);
          resolved();
        });
      }
    });
  }

};
