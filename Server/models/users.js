const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const NotificationSchema = new Schema({
    id : String,
    seen: {
        type: Boolean,
        default: false
    },
    unit:String,
    number:String,
    subject:String,
    name:String
});
const WriteUpSchema = new Schema({
    id : String,
    seen: {
        type: Boolean,
        default: false
    },
    grp:String,
    number:String,
    subject:String,
    name:String
});
const NoticeSchema = new Schema({
    id : String,
    seen: {
        type: Boolean,
        default: false
    },
    msubject:String,
    message:String
});

const UserSchema = new Schema({

  firstname: {
    type: String,
    required: [true, 'firstname is Required']
  },
  lastname: {
    type: String,
    required: [true, 'lastname is Required']
  },
  mobile: {
    type: String,
    required: [true, 'mobile is Required']
  },
  gender: {
    type: String,
    required: [true, 'gender is Required']
  },
  dob: {
    type: String,
    required: [true, 'dob is Required']
  },
  address: {
    type: String,
    required: [true, 'address is Required']
  },

  city: {
    type: String,
    required: [true, 'city is Required']
  },
  aadhaar: {
    type: String,
    required: [true, 'aadhaar is Required']
  },
  branch: {
    type: String,
    required: [true, 'branch is Required']
  },
  year: {
    type: String,
    required: [true, 'year is Required']
  },
  division: {
    type: String,
    required: [true, 'division is Required']
  },
  batch: {
    type: String,
    required: [true, 'batch is Required']
  },
  rollno: {
    type: String,
    required: [true, 'rollno is Required']
  },
  email: {
    type: String,
    required: [true, 'email is Required']
  },
  username: {
    type: String,
    required: [true, 'username is Required']
  },
  password: {
    type: String,
    required: [true, 'password is Required']
  },
  firebasetoken: {
    type: String,
    required: [true, 'Token']
  },
  auth: {
    type: String
  },
  notification:{
    type:[NotificationSchema]
  },
  writeup:{
    type:[WriteUpSchema]
  },
  notices:{
    type:[NoticeSchema]
  },
  created: {
    type: Date,
    default: Date.now
  }

});

const user = mongoose.model('user', UserSchema);
module.exports = user;
