package com.codingstrings.sns.utils.http;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.codingstrings.sns.screens.HomeActivity;
import com.codingstrings.sns.screens.SplashScreenActivity;
import com.codingstrings.sns.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;


public class DownloadQueue{
    public static NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    public int progress=0;
    public int id1=0;



    private static boolean Return = false;
    private static final int  MEGABYTE = 1024*1024;


    public static String FILE_NAME = "new";
    public static String FOLDER_NAME = "Assignments";
    public static String FILE_TYPE = ".pdf";

    private OutputStream output = null;
    private InputStream input = null;
    private String WEB_URL= "http://139.59.76.112:3000/api/file/";


    public boolean Download(String id,int urlType) {

        if(urlType == 0){
            WEB_URL= "http://139.59.76.112:3000/api/file/";
        }
        if(urlType == 1){
            WEB_URL= "http://139.59.76.112:3000/api/wfile/";
        }
        if(urlType == 2){
            WEB_URL= "http://139.59.76.112:3000/api/nfile/";
        }

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME);

        if(!folder.exists()) {
            folder.mkdirs();
        }

        FILE_NAME = id+FILE_TYPE;

        final File file = new File(folder, FILE_NAME);

        if(file.exists()) return true;

        else{

            final Context context = SplashScreenActivity.mGetContext();

            String token = HomeActivity.token;

            Map<String, String> params = new HashMap<String, String>();
            params.put("token", token);

            String mUrl = WEB_URL  + id + "/0";

            InputStreamVolleyRequest mRequest = new InputStreamVolleyRequest(Request.Method.POST, mUrl, new Response.Listener<byte[]>() {
                @Override
                public void onResponse(byte[] response) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    try {
                        if (response != null) {

                            Toast.makeText(context,"Download Started "+response.length,Toast.LENGTH_SHORT).show();

                            Log.d("DownloadQueueToken", "Response: " + response);

                            int count = 0;

                            try {

                                Log.d("DownloadQueueToken", "File ExistsA: "+ file.exists());




                                file.createNewFile();

                                Log.d("DownloadQueueToken", "File ExistsB: "+ file.exists());


                                Log.d("DownloadQueueToken", "File: "+file);


                                final long lenghtOfFile = response.length;


                                input = new ByteArrayInputStream(response);

                                byte data[] = new byte[MEGABYTE];

                                output = new FileOutputStream(file);

                                int total  = 0;

                                BufferedOutputStream bout = new BufferedOutputStream(output);

                                while ((count = input.read(data)) != -1) {

                                    total += count;
                                    int finalCount = count;
                                    int finalTotal = total;
                                    bout.write(data, 0, count);

                                }

                                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Download Complete").setContentText("Finished").setAutoCancel(true).setSound(defaultSoundUri);
                                mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE)  ;
                                notificationManager.notify(0,mBuilder.build());


                                Toast.makeText(context, "Download complete."+lenghtOfFile, Toast.LENGTH_LONG).show();
                                Return = true;
                            } catch (IOException e) {
                                e.printStackTrace();

                                Return = false;

                            }
                            finally {
                                if(output!=null)
                                {
                                    output.flush();
                                    output.close();
                                }
                                if(input!=null)
                                input.close();

                            }
                        }
                        else{
                            Toast.makeText(context,"Error Occor While Making Request",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, (HashMap<String, String>) params);

            RequestQueue mRequestQueue = Volley.newRequestQueue(context,
                    new HurlStack());

            mRequestQueue.add(mRequest);



            return Return;
        }



    }
}