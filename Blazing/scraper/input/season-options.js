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
    seasons: Object.assign({}, Media.prototype, {
      name: 'seasons',
      patterns: [{
        selector: {
          path: '#seasonList > div:nth-child(2) a', transform: ($, seasons) => {
            let urls = [];
            seasons.each(function (index, value) {
              urls.push($(value).attr('href'));
            });
            return urls;
          }
        },
        childSelectors: null
      }]
    })



  }
};