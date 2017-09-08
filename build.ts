console.log("*********START BUILDING APPLICATION**********");
var shell = require('shelljs');


var appName;
var packageName;
var packageFolder;
var serverPort;
var databaseType;
var applicationPrefix;
var extras;


process.argv.forEach(function (val, index, array) {

  console.log(index + ': ' + val);

  if (index === 2) {
  	appName = val;
  }

  if (index === 3) {
  	packageName = val;
  }

  if (index === 4) {
  	packageFolder = val;
  }

  if (index === 5) {
  	serverPort = val;
  }

  if (index === 6) {
  	databaseType = val;
  }

  if (index === 7) {
  	applicationPrefix = val;
  }

});

console.log("Application Name " + appName);

console.log("Package Name:       " + packageName);
console.log("Package Folder:     " + packageFolder);
console.log("Server Port:        " + serverPort);
console.log("Database Type :     " + databaseType);
console.log("Application Prefix: " + applicationPrefix);
console.log("Extras:             " + extras);

console.log('clean up directory'+'/data/'+appName); 

var rimraf = require('rimraf');
var fs = require('fs');

rimraf('data/'+appName, function () { 
		console.log('cleaned directory'); 
});

if (!fs.existsSync('data/'+appName)) fs.mkdirSync('data/'+appName,'0777', true);


console.log('Application Directory Created...'); 


//fs.createReadStream('templates/type1-yo-rc.json').pipe(fs.createWriteStream('data/'+appName+'/.yo-rc.json'));


console.log('Copied templates...'); 





var fileTo = '/apps/kapil/baboons/data/'+appName+'/.yo-rc.json';

//to do fix replace and remove following line for default template

shell.cp('-R', 'templates/type1-yo-rc.json', 'data/'+appName+'/.yo-rc.json');
shell.cp('-R', '.yo-rc.json', 'data/'+appName+'/.yo-rc.json');


 /* shell.exec('-i', 'application-name', appName, fileTo);
  shell.sed('-i', 'package-name', packageName, fileTo);
  shell.sed('-i', 'package-folder', packageFolder, fileTo);
  shell.sed('-i', 'server-port', serverPort, fileTo);
  shell.sed('-i', 'database-type', databaseType, fileTo);
  shell.sed('-i', 'application-prefix', applicationPrefix, fileTo);
  */

shell.cd('data/'+appName);

shell.exec('jhipster --regenerate');

shell.exec('zip -r --exclude=*node_modules* -X "../appName.zip" *');


