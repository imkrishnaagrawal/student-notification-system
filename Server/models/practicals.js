const mongoose = require('mongoose');
const Schema = mongoose.Schema;


const PracticalSchema = new Schema({

  name: {
    type: String,
    required: [true, 'Assignment Name is Required']
  },
  number: {
    type: String,
    required: [true, 'Assignment Number is Required']
  },
  Subject: {
    type: String,
    required: [true, 'Subject is Required']
  },
  year: {
    type: String,
    required: [true, 'Assignment Year is Required']
  },
  message: {
    type: String
  },
  filepath: {
    type: String
  },
  created: {
    type: Date,
    default: Date.now
  }
});

const practical = mongoose.model('practical', PracticalSchema);
module.exports = practical;
