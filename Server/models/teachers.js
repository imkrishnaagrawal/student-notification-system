const mongoose = require('mongoose');
const Schema = mongoose.Schema;


const TeacherSchema = new Schema({

  firstname: {
    type: String,
    required: [true, 'firstname is Required']
  },
  lastname: {
    type: String,
    required: [true, 'lastname is Required']
  },

  email: {
    type: String,
    required: [true, 'email is Required']
  },
  password: {
    type: String,
    required: [true, 'password is Required']
  },
  gender: {
    type: String
  },

  subject: {
    SET: String,
    SEP: String,
    TET: String,
    TEP: String,
    BET: String,
    BEP: String
  },
  auth: {
    type: String
  },
  created: {
    type: Date,
    default: Date.now
  }

});

const teacher = mongoose.model('teacher', TeacherSchema);
module.exports = teacher;
