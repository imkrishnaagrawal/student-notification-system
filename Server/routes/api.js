const express = require('express');
const path = require('path');
const router = express.Router();
const User = require('../models/users');
const Teacher = require('../models/teachers');
const Practical = require('../models/practicals');
const Assignment = require('../models/assignments');
const WriteUp = require('../models/writeups');
const Notice = require('../models/notice');
const con = require('../config');
var jwt = require('jsonwebtoken');
var multer = require('multer');
const nodejszip = require('nodejs-zip');
var Notification = require('./notification').Notification;
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


// Route For Sending Files To Client
router.post('/file/:id/:index', function(req, res) {
console.log(req.params.id);
  Assignment.find({
    _id: req.params.id
  }).then(function(data) {

    if(data[0].filepath.length <= req.params.index || true ){
    console.log(data[0].filepath);
    console.log(path.resolve(data[0].filepath[req.params.index]));
    res.download(path.resolve(data[0].filepath[req.params.index]));
  }else{
    res.json({
      success: false,
      message: 'File Not Found'
    });
  }

  });

});


router.post('/wfile/:id/:index', function(req, res) {
console.log(req.params.id);
  WriteUp.find({
    _id: req.params.id
  }).then(function(data) {
    console.log(data[0]);
    if(data[0].filepath.length <= req.params.index || true ){
    console.log(data[0].filepath);
    console.log(path.resolve(data[0].filepath[req.params.index]));
    res.download(path.resolve(data[0].filepath[req.params.index]));
  }else{
    res.json({
      success: false,
      message: 'File Not Found'
    });
  }

  });

});


router.post('/nfile/:id/:index', function(req, res) {
console.log(req.params.id);
  Notice.find({
    _id: req.params.id
  }).then(function(data) {

    if(data[0].filepath.length <= req.params.index || true ){
    console.log(data[0].filepath);
    console.log(path.resolve(data[0].filepath[req.params.index]));
    res.download(path.resolve(data[0].filepath[req.params.index]));
  }else{
    res.json({
      success: false,
      message: 'File Not Found'
    });
  }

  });

});


router.post('/data', function(req, res) {

  var token = req.body.token || req.params['token'] || req.headers['x-access-token'];
  var decoded = jwt.decode(token);

  User.findOne({
    email: decoded['data']
  }).then(function(data) {
    p = processSendAssignment(data);
    console.log(p);
    res.send(p);
  });
});


router.post('/wdata', function(req, res) {

  var token = req.body.token || req.params['token'] || req.headers['x-access-token'];
  var decoded = jwt.decode(token);
    console.log(decoded['data']);
  User.findOne({
    email: decoded['data']
  }).then(function(data) {
      console.log(data);
    p = processSendWriteups(data);
    console.log(p);
    res.send(p);
  });
});

router.post('/ndata', function(req, res) {

  var token = req.body.token || req.params['token'] || req.headers['x-access-token'];
  var decoded = jwt.decode(token);
  User.findOne({
    email: decoded['data']
  }).then(function(data) {
    p = processSendNotices(data);
    console.log(p);
    res.send(p);
  });
});





router.get('/', function(req, res) {
  res.json({
    message: 'Welcome!'
  });
  res.end()
});

router.get('/home', function(req, res) {

  var decoded = jwt.decode(req.cookies['token']);

  Teacher.findOne({
    "email": decoded['data']
  }).then(function(data) {
    console.log(data);
    res.render('home', {
      firstname: data.firstname,
      lastname: data.lastname,
      subject: data.subject
    });
    res.end()
  });

});
router.get('/writeups', function(req, res) {

  var decoded = jwt.decode(req.cookies['token']);

  Teacher.findOne({
    "email": decoded['data']
  }).then(function(data) {
    console.log(data);
    res.render('writeups', {
      firstname: data.firstname,
      lastname: data.lastname,
      subject: data.subject
    });

    res.end()
  });

});




router.get('/notices', function(req, res) {

  var decoded = jwt.decode(req.cookies['token']);

  Teacher.findOne({
    "email": decoded['data']
  }).then(function(data) {
    console.log(data);
    res.render('notices', {
      firstname: data.firstname,
      lastname: data.lastname,
      subject: data.subject
    });
    res.end()
  });

});


router.get('/logout', function(req, res) {
  console.log('logout');
  res.clearCookie('token');
  res.render('logout')
  res.end()
});






router.get('/youtube', function(req, res) {
  console.log('youtube');
  res.send('welcom')
  res.end();
});



router.get('/forgotpassword', function(req, res) {
  res.send('welcom')
  res.end();
});
router.get('/notification', function(req, res) {

  res.end();
});

router.post('/assignments', upload.any(), function(req, res, next) {
  // if you're here, the file should have already been uploaded

  var assignment = processAssignmentsData(req);
  console.log(req.body);
  Assignment.create(assignment).then(function(data) {
    UpdateString = "{";
    year = req.body.year == 'All' ? '' : req.body.year == 'S.E' ? 'S.E' : req.body.year == 'T.E' ? 'T.E' : req.body.year == 'B.E' ? 'B.E' : 'False';
    division = req.body.division == 'All' ? '' : req.body.division == 'A' ? 'A' : req.body.division == 'B' ? 'B' : 'False';
    batch = req.body.batch == 'All' ? '' : req.body.batch == '1st' ? '1st' : req.body.batch == '2nd' ? '2nd' : req.body.batch == '3rd' ? '3rd' : 'False';
    console.log(year);
    console.log(division);
    console.log(batch);
    if (false) {

    } else {
      UpdateString += year.length > 0 ? "year : " + "'" + year + "'" : '';
      UpdateString += division.length > 0 ? ", division : " + "'" + division + "'" : '';
      UpdateString += batch.length > 0 ? ", batch: " + "'" + batch + "'" : '';
      UpdateString += "}";
      console.log(UpdateString);

      var query = User.find({});


      if(year){
        query = query.where('year').equals(year);
          console.log(query)
        if(division){
            console.log(query)
          query = query.where('division').equals(division);
          if(batch){
              console.log(query)
            query = query.where('batch').equals(batch);

          }
        }

      }

      query.update( {
          $push: {
            notification: {
              _id: data._id,
              seen: false,
              unit: data.unit,
              number: data.number,
              subject: data.subject,
              name: data.name
            }
          }
        }, function (err, count) {

        console.log(count)
        if(count){

//Notification(assignment);

          res.render('success',{message:"Operation Sucessful"});

          res.end();
        }
        else{
        res.render('success',{message:"Try Again"});

        res.end();
      }
      });

    }
  });

});
router.post('/writeups', upload.any(), function(req, res, next) {
  // if you're here, the file should have already been uploaded

  var writeup = processWriteUpsData(req);
  console.log(req.body);
  WriteUp.create(writeup).then(function(data) {
    UpdateString = "{";
    year = req.body.year == 'All' ? 'ComputerDepartment' : req.body.year == 'S.E' ? 'S.E' : req.body.year == 'T.E' ? 'T.E' : req.body.year == 'B.E' ? 'B.E' : 'False';
    division = req.body.year == 'All' ? '' : req.body.division == 'A' ? 'A' : req.body.division == 'B' ? 'B' : 'False';
    batch = req.body.year == 'All' ? '' : req.body.batch == '1st' ? '1st' : req.body.batch == '2nd' ? '2nd' : req.body.batch == '3rd' ? '3rd' : 'False';
    console.log(year);
    console.log(division);
    console.log(batch);
    if (year == 'False' || division == 'False' || batch == 'False') {
      res.render('sucess',{message:'Try Again'});
      res.end()
    } else {
      UpdateString += year.length > 0 ? "year : " + "'" + year + "'" : '';
      UpdateString += division.length > 0 ? ", division : " + "'" + division + "'" : '';
      UpdateString += batch.length > 0 ? ", batch: " + "'" + batch + "'" : '';
      UpdateString += "}";
      console.log(UpdateString);



    }
  });

});

router.post('/notice', upload.any(), function(req, res, next) {
  // if you're here, the file should have already been uploaded

  var notice = processNoticeData(req);
  console.log(req.body);
  Notice.create(notice).then(function(data) {
    UpdateString = "{";
    year = req.body.year == 'All' ? 'ComputerDepartment' : req.body.year == 'S.E' ? 'S.E' : req.body.year == 'T.E' ? 'T.E' : req.body.year == 'B.E' ? 'B.E' : 'False';
    division = req.body.year == 'All' ? '' : req.body.division == 'A' ? 'A' : req.body.division == 'B' ? 'B' : 'False';
    batch = req.body.year == 'All' ? '' : req.body.batch == '1st' ? '1st' : req.body.batch == '2nd' ? '2nd' : req.body.batch == '3rd' ? '3rd' : 'False';
    console.log(year);
    console.log(division);
    console.log(batch);
    if (year == 'False' || division == 'False' || batch == 'False') {
      res.render('sucess',{message:'Try Again'});
      res.end()
    } else {
      UpdateString += year.length > 0 ? "year : " + "'" + year + "'" : '';
      UpdateString += division.length > 0 ? ", division : " + "'" + division + "'" : '';
      UpdateString += batch.length > 0 ? ", batch: " + "'" + batch + "'" : '';
      UpdateString += "}";
      console.log(UpdateString);

      User.update(UpdateString, {
        $push: {
          notices: {
            _id: data._id,
            seen: false,
            msubject:data.msubject,
            subject: data.subject,
            message: data.message
          }
        }
      }, {
        multi: true
      }).then(function(userdata) {

          Notification(notice);

          res.render('success',{message:"Operation Sucessful"})
          res.end()

      });
    }
  });

});

function processNoticeData(req) {
  console.log("Pocessing Notices Data");
  var data = req.body;
  notice = {};
  notice['subject'] = data.subject;
  notice['msubject'] = data.msubject;
  notice['year'] = data.year;
  notice['division'] = data.division;
  notice['batch'] = data.division;
  notice['message'] = data.message;
  notice['filepath'] = [];
  req.files.forEach(function(file) {
    notice['filepath'].push(file.path);
  });
  console.log("Finishing...");
  return notice;

}



function processWriteUpsData(req) {
  console.log("Pocessing Assignment Data");
  var data = req.body;
  writeup = {};
  writeup['number'] = data.assignmentno;
  writeup['name'] = data.assignmentname;
  writeup['subject'] = data.subject;
  writeup['grp'] = data.grp;
  writeup['year'] = data.year;
  writeup['division'] = data.division;
  writeup['batch'] = data.division;
  writeup['message'] = data.message;
  writeup['filepath'] = [];
  req.files.forEach(function(file) {
    writeup['filepath'].push(file.path);
  });
  console.log("Finishing...");
  return writeup;

}

function processAssignmentsData(req) {
  console.log("Pocessing Assignment Data");
  var data = req.body;
  assignment = {};
  assignment['number'] = data.assignmentno;
  assignment['name'] = data.assignmentname;
  assignment['subject'] = data.subject;
  assignment['unit'] = data.unit;
  assignment['year'] = data.year;
  assignment['division'] = data.division;
  assignment['batch'] = data.division;
  assignment['message'] = data.message;
  assignment['filepath'] = [];
  req.files.forEach(function(file) {
    assignment['filepath'].push(file.path);
  });
  console.log("Finishing...");
  return assignment;

}



function processSendAssignment(data) {
  req = {};

  req.firstname = data.firstname;
  req.lastname = data.lastname;
  req.notification = [];
  req.notification = data.notification;
  return req;
}

function processSendWriteups(data) {
  req = {};

  req.firstname = data.firstname
  req.lastname = data.lastname;
  req.writeup = [];
  req.writeup = data.writeup;
  return req;
}
function processSendNotices(data) {
  req = {};

  req.firstname = data.firstname;
  req.lastname = data.lastname;
  req.notices = [];
  req.notices = data.notices;
  return req;
}


/*
router.use(function(req, res, next) {
  // check header or url parameters or post parameters for token
  var token = req.body.token || req.params['token'] || req.headers['x-access-token'] || req.cookies['token'];
  // decode token
  console.log("token");
  if (token) {
    // verifies secret and checks exp
    console.log("token");
    jwt.verify(token, con.secret, function(err, decoded) {
      if (err) {
        res.json({
          name: 'TokenExpiredError',
          message: err,
          expiredAt: 1408621000
        });
      }
      next();
      //Authenticated
    });

  } else {
    // if there is no token
    // return an error
    console.log("403");
    return res.json({
      success: false,
      message: 'No token provided.'
    });
  }
});
*/



module.exports = router;
