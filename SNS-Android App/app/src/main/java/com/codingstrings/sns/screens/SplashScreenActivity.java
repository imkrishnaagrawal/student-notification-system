package com.codingstrings.sns.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.codingstrings.sns.sql.SqlToken;
import com.codingstrings.sns.R;

/**
 * Created by root on 10/9/17.
 */

public class SplashScreenActivity extends Activity{

    public static Context context;
    private SQLiteDatabase readAble = null;
    private SqlToken sqLiteDatabase = null;
    static String token;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {

            ActivityCompat.requestPermissions(SplashScreenActivity.this,
                    new String[] {
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    100);

            context = getApplicationContext();

            sqLiteDatabase = new SqlToken(context);
            readAble       = sqLiteDatabase.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + SqlToken.TABLE_NAME ;
            final Cursor cursor      = readAble.rawQuery(selectQuery, null);
            Log.d("Cursor Count", "onCreate: "+cursor.getCount());


            new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if(cursor!=null && cursor.getCount()>0){

                    Intent home = new Intent(context, HomeActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(home);
                    cursor.close();
                    readAble.close();
                }
                else{
                    Intent login = new Intent(context, LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(login);

                }

            }
        },500);
        }catch (Exception e ){}

    }


    public static Context mGetContext(){return context;}


}
