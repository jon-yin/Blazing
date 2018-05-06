const request = require('request-promise');
const fs = require('fs');
const readline = require('readline');

module.exports.OMDBParser = class OMDBParser {
  constructor(queryStrings, options) {
    this.queryStrings = queryStrings;
    this.opts = options;
  }

  /* input file name required */
  search() {
    var self = this;

    let searchAPI = (data, cb) => {
      let query = {
        url: data
      };
      return request(query).then((response) => {
        let rslt = JSON.parse(response, null, 2);
        rslt = cb(rslt);
        return new Promise(function (res1, resp1) {
          console.info('rslt from... ' + data);
          res1(rslt);
        });
      });
    };

    let selectMediaData = (media, mediaType) => {
      var mediaEntry = {};
      switch (mediaType) {
        case self.opts.jsonKeys.movieKey:
          mediaEntry[self.opts.jsonKeys.movieKey] = (Object.assign({
            name: media.name || media.title,
            url: media.url,
            year: media.year,
            meterClass: (media.meterClass && media.meterClass !== 'N/A') ?
              media.meterScore : 'N/A'
          }));
          // add celebrities (only available in movies)
          mediaEntry[self.opts.jsonKeys.castKey] = [];
          media[self.opts.jsonKeys.castKey].forEach((celeb) => {
            mediaEntry[self.opts.jsonKeys.castKey].push(celeb);
          });
          break;
        case self.opts.jsonKeys.tvshowsKey:
          mediaEntry[self.opts.jsonKeys.tvshowsKey] = Object.assign({
            name: media.name || media.title,
            url: media.url,
            startYear: (media.startYear !== 0) ? media.startYear : 0,
            endYear: (media.endYear !== 0) ? media.endYear : 0,
            meterClass: (media.meterClass && media.meterClass !== 'N/A') ?
              media.meterScore : 'N/A'
          });
          break;
        default:
          console.error('bad media entry');
      }
      return mediaEntry;
    };

    let parsePosterAndBoxOffice = function (jsonResponse) {
      let o = {};
      // property names capitalization 
      o[self.opts.jsonKeys.boxOffice] = jsonResponse[self.opts.jsonKeys.boxOffice];
      o[self.opts.jsonKeys.poster] = jsonResponse[self.opts.jsonKeys.poster];
      return o;
    };

    let parseRottenTomatoesSearchJSON = function (jsonResponse) {
      var jsonResult = {};
      jsonResult[self.opts.jsonKeys.movieKey] = [];
      jsonResult[self.opts.jsonKeys.tvshowsKey] = [];
      jsonResult[self.opts.jsonKeys.castKey] = [];
      if (jsonResponse[self.opts.jsonKeys.movieCountKey]) {
        jsonResponse[self.opts.jsonKeys.movieKey].forEach((movie) => {
          let mediaRslt = selectMediaData(movie, self.opts.jsonKeys.movieKey);
          // returns an object with two arrays, [movie, cast]
          // mediaRslt[this.opts.jsonKeys.movieKey] is an object, not array
          jsonResult[self.opts.jsonKeys.movieKey].push(mediaRslt[self.opts.jsonKeys.movieKey]);

          mediaRslt[self.opts.jsonKeys.castKey].forEach((castMember) => {
            jsonResult[self.opts.jsonKeys.castKey].push(castMember);
          });
        });
      }
      if (jsonResponse[self.opts.jsonKeys.tvCountKey]) {
        jsonResponse[self.opts.jsonKeys.tvshowsKey].forEach((tvshow) => {
          let mediaRslt = selectMediaData(tvshow, self.opts.jsonKeys.tvshowsKey);
          jsonResult[self.opts.jsonKeys.tvshowsKey].push(mediaRslt[self.opts.jsonKeys.tvshowsKey]);
        });
      }
      return jsonResult;
    };

    // runs a function for each element of an array synchronously with an added delay
    let runDelayedPromises = (items, cb, cbArg, delayTime) => {
      let queryResults = [];
      let delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));
      // returns a promise with the query results
      return items.reduce(function (promise, m) {
        return promise.then(function (promiseResult) {
          if (promiseResult !== undefined)
            queryResults.push(promiseResult[1]); // the first element is always undef
          return Promise.all([delay(delayTime), cb(m, cbArg)]);
        });
      }, Promise.resolve()).then((lastQuery) => {
        queryResults.push(lastQuery[1]);
        return Promise.resolve(queryResults);
      });
    };

    let mergeJsonObjects = (jsonObj) => {
      let jsonFinal = {};
      jsonObj.forEach((keyName) => {
        Object.keys(keyName).forEach((key) => {
          keyName[key].forEach(val => {
            if (jsonFinal.hasOwnProperty(key) === false)
              jsonFinal[key] = [];
            jsonFinal[key].push(val);
          });
        });
      });
      return jsonFinal;
    };

    let runQueries = (queryStrings) => {

      return new Promise(function (resolved, rejected) {

        runDelayedPromises(queryStrings, searchAPI, parsePosterAndBoxOffice, self.opts.reqDelay)
          .then((queryResults) => {
            console.log(queryResults);
            resolved(mergeJsonObjects(queryResults));
          });
      });
    };

    return runQueries(this.queryStrings);
  }

};