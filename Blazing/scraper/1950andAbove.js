// get rid of movies before 1950

const fs = require('fs');

// const inputFile = './output/parsedMovies/pmovies00.json';

try {

  var json = JSON.parse(fs.readFileSync(process.argv[2]));
  // var json = JSON.parse(fs.readFileSync(inputFile));

  let newJson = [];
  json.forEach(m => {
    if (parseInt(m.premiered.match(/\d\d\d\d$/)) >= 1950) {
      newJson.push(m);
    }
  });

  // console.log(newJson);

  let f = JSON.stringify(newJson, null, 4);
  // fs.writeFileSync('newJson.json', f);
  fs.writeFileSync(process.argv[3], f);
}
catch (e) {
  console.error('eerrrr');
}