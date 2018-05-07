function Media() {
  this.name = '';
  this.childUrl = null;
  this.c = 0;
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
  return this.patterns[this.c || 0];
}

Media.prototype.nextPattern = function () {
  let p = this.patterns[++this.c];
  if (p) {
    return p;
  } else {
    this.c = 0;
  }
}

module.exports = {
  host: 'https://www.rottentomatoes.com',
  reqDelay: 1000,
  mediaSelectors: {

    summary: Object.assign({}, Media.prototype, {
      name: 'summary',
      patterns: [{
        selector: { path: '#movieSynopsis', transform: null },
        childSelectors: null
      }]
    }),

    premiered: Object.assign({}, Media.prototype, {
      name: 'premiered',
      patterns: [{
        selector: { path: 'li.meta-row:nth-child(2) > div:nth-child(2)', transform: null },
        childSelectors: null
      }]
    }),

    network: Object.assign({}, Media.prototype, {
      name: 'network',
      patterns: [{
        selector: { path: 'li.meta-row:nth-child(1) > div:nth-child(2)', transform: null },
        childSelectors: null
      }]
    }),

    episodes: Object.assign({}, Media.prototype, {
      name: 'episodes',
      patterns: [{
        selector: {
          path: '#episode-list-root', transform: null
        },
        childSelectors: [
          {
            name: 'episodeUrl',
            selector: {
              path: 'div:nth-child(1) > div > div:nth-child(1) > a:nth-child(1)',
              transform: ($, episodes) => {
                let urls = [];
                episodes.each(function (index, value) {
                  urls.push($(value).attr('href'));
                });
                return urls;
              }
            },
            childSelectors: null
          },
          {
            name: 'airDate',
            selector: {
              path: 'div:nth-child(1) > div > div:nth-child(2) > div:nth-child(2)',
              transform: ($, airDates) => {
                let ads = [];
                airDates.each(function (i, airDate) {
                  ads.push($(this).text().replace(/Air Date:/, '').trim());
                });
                return ads;
              }
            },
            childSelectors: null
          }
        ]
      }]
    }),

  }
};