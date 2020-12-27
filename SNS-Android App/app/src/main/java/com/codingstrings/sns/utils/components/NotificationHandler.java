package com.codingstrings.sns.utils.components;

import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.codingstrings.sns.R;

/**
 * Created by krishna on 9/19/17.
 */

public class NotificationHandler extends FirebaseMessagingService {


    private static final String TAG = "1";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {

                //scheduleJob();
            } else {

                //handleNow();
            }
            String title = remoteMessage.getData().get("t");
            String text = remoteMessage.getData().get("a");
            text += remoteMessage.getData().get("b");
            text += remoteMessage.getData().get("c");

            Log.d(TAG, "onMessageReceived: "+text);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher_round).setContentTitle(title).setContentText(text).setAutoCancel(true).setSound(defaultSoundUri);

            mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE)  ;
            notificationManager.notify(0,mBuilder.build());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }


}
