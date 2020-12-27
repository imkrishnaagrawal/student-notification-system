const mongoose = require('mongoose');
const Schema = mongoose.Schema;


const NoticeSchema = new Schema({

  subject: {
    type: String,
    required: [true, 'Subject is Required']
  },
  msubject: {
    type: String,
    required: [true, 'Subject is Required']
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

const notice = mongoose.model('notice', NoticeSchema);
module.exports = notice;
