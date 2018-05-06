function Media() {
  this.name = '';
  this.patterns = [];
}

const Helpers = {
  arrayFromChildNodes: function ($, cheerioArray) {
    let ls = [];
    cheerioArray.each(function () {
      ls.push($(this).text().trim());
    });
    return ls;
  }
};

Media.prototype.pattern = function () {
  return this.patterns[0];
}

Media.prototype.nextPattern = function () {
  return this.patterns.shift();
}

module.exports = {
  host: 'https://www.rottentomatoes.com',
  reqDelay: 1000,
  mediaType: 'tvshow',
  mediaSelectors: {
    description: Object.assign({}, Media.prototype, {
      name: 'description',
      patterns: [{
        selector: { path: '#movieSynopsis', transform: null },
        childSelectors: null
      }]
    }),

    genres: Object.assign({}, Media.prototype, {
      name: 'genres',
      patterns: [{
        selector: { path: 'li.meta-row:nth-child(1) > div:nth-child(2)', transform: null },
        childSelectors: [{
          name: null,
          selector: {
            path: 'a',
            transform: Helpers.arrayFromChildNodes
          },
          childSelectors: null
        }]
      },
      {
        selector: { path: 'li.meta-row:nth-child(1) > div:nth-child(2)', transform: null },
        childSelectors: null
      }

      ]

    }),

    network: Object.assign({}, Media.prototype, {
      name: 'network',
      patterns: [{
        selector: { path: 'li.meta-row:nth-child(2) > div:nth-child(2)', transform: null },
        childSelectors: null
      },
      ]
    }),

    premiere: Object.assign({}, Media.prototype, {
      name: 'premiere',
      patterns: [{
        selector: { path: 'li.meta-row:nth-child(3) > div:nth-child(2)', transform: null },
        childSelectors: null
      },
      ]
    }),

    execProducers: Object.assign({}, Media.prototype, {
      name: 'execProducers',
      patterns: [{
        selector: { path: '#episode-list-root', transform: null },
        childSelectors: [
          {
            name: 'executiveUrl',
            selector: {
              path: 'a',
              transform: ($, celebEntries) => {
                let urls = [];
                celebEntries.each(function (index, value) {
                  urls.push($(value).attr('href'));
                });
                return urls;
              }
            },
            childSelectors: null
          },
          {
            name: 'executiveName',
            selector: {
              path: 'a', transform: Helpers.arrayFromChildNodes
            },
            childSelectors: null
          },
        ]
      }],
    }),

    cast: Object.assign({}, Media.prototype, {
      name: 'cast',
      patterns: [{
        selector: { path: '.castSection', transform: null },
        childSelectors: [
          {
            name: 'celebrityURL',
            selector: {
              path: 'div.cast-item > div > a',
              transform: ($, celebEntries) => {
                let urls = [];
                celebEntries.each(function (index, value) {
                  urls.push($(value).attr('href'));
                });
                return urls;
              }
            },
            childSelectors: null
          },
          {
            name: 'celebrityName',
            selector: {
              path: 'div.cast-item > div > a > span', transform: Helpers.arrayFromChildNodes
            },
            childSelectors: null
          },
          {
            name: 'character',
            selector: {
              path: 'div.cast-item > div > span',
              transform: ($, celebEntries) => {
                let chars = [];
                celebEntries.each(function () {
                  chars.push($(this).text().trim().replace(/^as /, ''));
                });
                return chars;
              }
            },
            childSelectors: null
          }
        ]
      }]
    }),

    episodes: Object.assign({}, Media.prototype, {
      name: 'episodes',
      patterns: [{
        selector: { path: '#episode-list-root', transform: null },
        childSelectors: [
          {
            name: 'episodeUrl',
            selector: {
              path: 'a',
              transform: ($, celebEntries) => {
                let urls = [];
                celebEntries.each(function (index, value) {
                  urls.push($(value).attr('href'));
                });
                return urls;
              }
            },
            childSelectors: null
          },
          {
            name: 'episodeTitle',
            selector: {
              path: 'a', transform: Helpers.arrayFromChildNodes
            },
            childSelectors: null
          },
        ]
      }],
    })

  }
};