package com.codingstrings.sns.screens;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstrings.sns.models.Notice;
import com.codingstrings.sns.utils.adapters.RVAdapterNotice;
import com.codingstrings.sns.sql.SqlNotice;
import com.codingstrings.sns.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends Activity {


    private List<Notice> notice = null;
    private RecyclerView rv= null;
    private SQLiteDatabase readAble = null;
    private SqlNotice sqLiteDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity3);

        rv=(RecyclerView)findViewById(R.id.nrv);

        sqLiteDatabase = new SqlNotice(SplashScreenActivity.mGetContext());
        readAble       = sqLiteDatabase.getReadableDatabase();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();



    }

    private void initializeData(){

        notice = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SqlNotice.TABLE_NAME;
        Cursor cursor      = readAble.rawQuery(selectQuery, null);

        Log.d("DataBase Count", "initializeData: "+cursor.getCount());

        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
                do {

                    notice.add(new Notice(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)

                    ));
                    Log.d("Id",cursor.getString(0));
                    Log.d("subject",cursor.getString(1));
                    Log.d("message",cursor.getString(2));
                    Log.d("seen",cursor.getString(3));
                } while (cursor.moveToNext());
                cursor.close();

        }
        else{
            Log.d("DataBase Count", "Cursor:Empty ");
        }

    }
    private void initializeAdapter(){
        RVAdapterNotice adapter = new RVAdapterNotice(notice);
        rv.setAdapter(adapter);
    }

}
