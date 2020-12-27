const express = require('express');
const path = require('path');
const uploadrouter = express.Router();
var multer = require('multer');

var storage = multer.diskStorage({
  destination: function(request, file, callback) {
    callback(null, './uploads/');
  },
  filename: function(request, file, callback) {
    callback(null, file.originalname)
  }
});

var upload = multer({
  storage: storage
});

uploadrouter.post('/file', upload.single('userFile'), function(req, res, next) {
  // if you're here, the file should have already been uploaded
  console.log(req.file);
  console.log('file uploaded1');
  res.redirect('/api/assignments');

});




module.exports = uploadrouter;
