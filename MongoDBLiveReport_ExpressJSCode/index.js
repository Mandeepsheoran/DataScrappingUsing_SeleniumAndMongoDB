// Import express
var express = require('express');
var app = express();

// Get MongoClient 
var MongoClient = require('mongodb').MongoClient;

//db url, collection name and db name
const dburl = 'mongodb://localhost:27017';
const dbname = 'evisadb';     //This database is already created in mongodb
const collname = 'SelniumMongoDB';     //Data i.e. documents are already inserted in this collection.

// process root url '/'
app.get('/', function(req, res) {
    console.log('Starting the mongoDB connection');

  // connect to DB
   MongoClient.connect(dburl, function(err, client) {
      if (!err) {
	// Get db
  const db = client.db(dbname);

  // Get collection
  const collection = db.collection(collname);

  console.log('DB name and collection name are collected');
  console.log(collection.find());

  //Note: Currently code is not working after this line
  // Find all documents in the collection and covert it in array
  collection.find({}).toArray(function(err, todos) {    // Coverting doc collection in array and passing the promise using array vraible "todos"
    if (!err) {
        console.log('No error after coverting to array hence proceeding for table');

      // write HTML output
      var output = '<html><header><title>Automation Report</title></header><body>';    //This will be page title
      output += '<h1>My Test Automation Live Report</h1>';                              //This will be page heading
      output += '<table border="1"><tr> <td><b>' + 'Class Name' + '</b></td> <td><b>' + 'Method Name' + '</b></td> <td><b>' + 'Priority' + '</b></td> <td><b>' + 'Status' + '</b>   </td></tr>';
      console.log('Page title and page header are created');
      // process todo list...Fetching the data from mongo db using this array variable
      todos.forEach(function(todo){
        output += '<tr><td>' + todo.MethodName + '</td><td>' + todo.ClassName  + '</td><td>' + todo.Priority + '</td><td>' + todo.Status + '</td></tr>';
      });
      console.log('Table header values are passed');

      // write HTML output (ending)
      output += '</table></body></html>'
      console.log('Printed HTML output');
      // send output back
      res.send(output);
      console.log('Output is sent back');
      // log data to the console as well
      console.log(todos);
      console.log('Data logged to console');
    }
  });

  // close db client
  client.close();
  console.log('MongoDb connection closed');
}
}); });

console.log('Express js listening port is given below');
// listen on port 3000
app.listen(3000, function() { console.log('Example app listening on port 3000!') });