const mongoose = require('mongoose');
const Schema = mongoose.Schema;


const AssignmentSchema = new Schema({

  number: {
    type: String,
    required: [true, 'Assignment Number is Required']
  },
  name: {
    type: String,
    required: [true, 'Assignment Name is Required']
  },
  subject: {
    type: String,
    required: [true, 'Subject is Required']
  },
  unit: {
    type: String
  },
  year: {
    type: String,
    required: [true, 'Assignment Year is Required']
  },
  division:{
    type:String

  },
  batch:{
    type:String
  },
  message: {
    type: String
  },
  filepath: {
    type: [String]
  },
  created: {
    type: Date,
    default: Date.now
  }
});

const assignment = mongoose.model('assignment', AssignmentSchema);
module.exports = assignment;
