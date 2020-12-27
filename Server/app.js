const express = require('express')
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const ejs = require('ejs')
const helmet = require('helmet')
const config = require('./config');
const cookieParser = require('cookie-parser')
var RateLimit = require('express-rate-limit');
const User = require('./models/users');
const Teacher = require('./models/teachers');
const Notice = require('./models/notice')
const Assignment = require('./models/assignments');

const con = require('./config');
var jwt = require('jsonwebtoken');

//set up express app
const app = express();

app.enable('trust proxy');

var limiter = new RateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 100, // limit each IP to 100 requests per windowMs
  delayMs: 0 // disable delaying - full speed until the max limit is reached
});

//  apply to all requests
app.use(limiter);
app.use(cookieParser());
app.use(helmet({
  frameguard: {
    action: 'deny'
  }

}))


app.set('view engine', 'ejs');
//Mongodb Connection
const mongoUri = config.database;
mongoose.connect(mongoUri, {
  socketTimeoutMS: 0,
  keepAlive: true,
  reconnectTries: 30,
 useMongoClient: true });
mongoose.Promise = global.Promise;


//Enable Body Parser
app.use(bodyParser.urlencoded({
  extended: false
}));
app.use(bodyParser.json());

app.use('/', express.static(__dirname + '/views'));
app.use(express.static(__dirname + '/uploads'));


//initizlie routes
app.use('/api', require('./routes/api'));
app.use('/upload', require('./routes/uploads'));

app.use(function(err, req, res, next) {
  res.status(422).send(err)
});

app.get('/', function(req, res) {
  res.redirect('/login')
});

app.get('/login', function(req, res) {
  res.render('index')
});

app.get('/register', function(req, res) {
  res.render('register')
});

app.post('/login', function(req, res) {
  console.log('Route: /login : \n'+req.body);
  User.findOne({
    email: req.body.email
  }).then(function(data) {
    console.log(data);
    if (!data) {
      res.json({
        success: false,
        message: 'Authentication failed. User not found.'
      });

    } else if (data) {

      if (data.password != req.body.password) {
        res.json({
          success: false,
          message: 'Authentication failed. Wrong password.'
        });
      } else {
        var T = jwt.sign({
          data: req.body.email
        }, con.secret, {
          expiresIn: '15h'
        });
        res.json({
          success: true,
          message: 'Authentication Successful.',
          token: T
        });
      }
    }
  });
});

app.post('/tlogin', function(req, res) {
  Teacher.findOne({
    "email": req.body.email
  }).then(function(data) {
    if (!data) {
      res.render('failed',{message:'User Not Found'})
    } else if (data) {

      if (data.password != req.body.password) {
        res.render('failed',{message:'Wrong password'})
      
      } else {
        var token = jwt.sign({
          data: req.body.email
        }, con.secret, {
          expiresIn: '5h'
        });
        res.cookie('token', token)
        res.redirect('api/home');
      }
    }
  });
});



app.post('/register', function(req, res, next) {
  console.log(req.body);
  User.create(req.body).then(function(data) {
    res.json({
      success: true,
      message: 'Registration Successful.'
    });
    res.end();
  }).catch(next);
});

app.post('/tregister', function(req, res, next) {
  console.log(req)
  var teacher = processTeachersData(req.body)
  Teacher.create(teacher).then(function(data) {
    res.redirect('login');

  }).catch(next);
});


app.listen(process.env.port || 3000, function() {
  console.log("Listening on 3000")
});



function processTeachersData(data) {
  console.log("Proessing Teachers Data");
  info = {};

  info['firstname'] = data.firstname;
  info['lastname'] = data.lastname;
  info['email'] = data.email;
  info['password'] = data.password;
  info['gender'] = data.gender;
  info['subject'] = {};
  info['subject']['SET'] = data.SET;
  info['subject']['SEP'] = data.SEP;
  info['subject']['TET'] = data.TET;
  info['subject']['TEP'] = data.TEP;
  info['subject']['BET'] = data.BET;
  info['subject']['BEP'] = data.BEP;
  info['auth'] = "some auth";
  console.log("Finishing...");
  return info;
}
