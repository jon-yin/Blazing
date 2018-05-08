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
  reqDelay: 1200,
  notRequired: [],
  mediaSelectors: {
    portrait: Object.assign({}, Media.prototype, {
      name: 'portrait',
      patterns: [{
        selector: {
          path: '.celebHeroImage', transform: ($, i) => {
            let imageurl = i.attr('style').replace(/background-image:url/, '');
            imageurl = imageurl.slice(2, -2);
            // console.log(imageurl);
            return imageurl;
          }
        }, childSelectors: null
      }]
    }),
  }
}; 