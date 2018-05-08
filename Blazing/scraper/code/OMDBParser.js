const request = require('request-promise');

module.exports.OMDBParser = class OMDBParser {
  constructor(queryStrings, options) {
    this.queryStrings = queryStrings;
    this.opts = options;
  }

  /* input file name required */
  search() {
    var self = this;

    let searchAPI = (data, cb) => {
      return request(data.query).then((response) => {
        let rslt = JSON.parse(response, null, 2);
        rslt = cb(rslt);
        console.info('rslt from... ' + data.query);

        return Promise.resolve({
          url: data.url,
          // handle multiple types if needed
          posterPath: data.url + '_poster.jpg',
          posterSrc: rslt.Poster
        });

      }).catch(err => {
        console.log('request timed out, skipping...');
        return Promise.resolve();
      });
    };

    let parsePosterAndBoxOffice = function (jsonResponse) {
      let o = {};
      // property names capitalization 
      o[self.opts.jsonKeys.boxOffice] = jsonResponse[self.opts.jsonKeys.boxOffice];
      o[self.opts.jsonKeys.poster] = jsonResponse[self.opts.jsonKeys.poster];
      return o;
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

    let runQueries = (queryStrings) => {

      return new Promise(function (resolved, rejected) {

        runDelayedPromises(queryStrings, searchAPI, parsePosterAndBoxOffice, self.opts.reqDelay)
          .then((queryResults) => {
            // console.log(queryResults);
            resolved(queryResults);
          });
      });
    };

    return runQueries(this.queryStrings);
  }

};