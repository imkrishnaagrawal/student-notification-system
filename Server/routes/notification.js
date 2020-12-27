
const FCM = require('fcm-node')
var serverKey = require('../google-services.json').private_key;
var fcm = new FCM(serverKey)

module.exports = {
  Notification: function(assignment){

  console.log('Push Notification'+assignment.number);
  var Year = assignment.year=='All'?"ComputerDepartment":assignment.year;
  var SendTo = Year;
  if(Year!= "ComputerDepartment")
    {
       var division = assignment.division=='A' || assignment.division=='B' ? assignment.division : null;
          if(division!=null){
            SendTo+='_'+division;
            var batch = assignment.batch=='1st' || assignment.batch=='2nd' || assignment.batch=='3rd'? assignment.batch: null;
            if(batch!=null)
            SendTo+='_'+batch;
          }
    }



  var message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
    //to: 'eijtvJJAx3o:APA91bHuzLYkRn2YrnhwbkhQpsL_sdT6bVUJLC89k1slgjsBj9mCQrsWepb8mUfYlvx39bbvJHYg4v0MHArvCNl2YAYKYBRQrTl2ft18-SNwX_jf8WxUXs7u1ufzD97wlGFoFWowsUHA',
    //collapse_key: 'your_collapse_key',
    to: '/topics/ComputerDepartment',
    //   notification: {
    //       title: 'Title of your push notification',
    //       body: 'Body of your push notification'
    //  },

    data: { //you can send only notification or only data(or include both)
      t: 'my value',
      a: 'www.google.com',
      b: 'www.facebook.com',
      c: 'www.yahoo.com'

    }
  }
  fcm.send(message, function(err, response) {
    if (err) {
      console.log("Something has gone wrong!")
    } else {
      console.log("Successfully sent with response: ", response)
    }
  })
return;
}

};
