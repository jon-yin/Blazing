const numRating = {
  patterns: [
    // r/R
    /\d?\.?\d+\/(4|5|10)/
  ],
  normalize: function (rating) {
    let match = null;
    for (let i = 0; i < this.patterns.length; i++) {
      match = rating.match(this.patterns[i]);
      if (match !== null) {
        break;
      }
    }
    return match;
  }
};

const alphaRating = {
  Ap: ['A+', 100],
  A: ['A', 93],
  Am: ['A-', 90],
  Bp: ['B+', 87],
  B: ['B', 83],
  Bm: ['B-', 80],
  Cp: ['C+', 77],
  C: ['C', 73],
  Cm: ['C-', 70],
  Dp: ['D+', 67],
  D: ['D', 63],
  Dm: ['D-', 60],
  F: ['F', 1],
  normalize: function (rating) {

  }
};

module.exports.normalize = function (rating) {
  switch (rating) {
    case 1:

      break;
  }
};