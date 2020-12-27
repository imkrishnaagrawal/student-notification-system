package com.codingstrings.sns.screens;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstrings.sns.sql.SqlWriteUps;
import com.codingstrings.sns.utils.adapters.WriteUpsAdapter;
import com.codingstrings.sns.models.WriteUp;
import com.codingstrings.sns.R;

import java.util.ArrayList;
import java.util.List;

public class WriteUpsActivity extends Activity {


    private List<WriteUp> writeups = null;
    private RecyclerView rv= null;
    private SQLiteDatabase readAble = null;
    private SqlWriteUps sqLiteDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity2);

        rv=(RecyclerView)findViewById(R.id.wrv);

        sqLiteDatabase = new SqlWriteUps(SplashScreenActivity.mGetContext());
        readAble       = sqLiteDatabase.getReadableDatabase();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();



    }

    private void initializeData(){

        writeups = new ArrayList<>();

       

        String selectQuery = "SELECT  * FROM " + SqlWriteUps.TABLE_NAME ;
        Cursor cursor      = readAble.rawQuery(selectQuery, null);

        Log.d("DataBase Count", "initializeData: "+cursor.getCount());

        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
                do {

                    writeups.add(new WriteUp(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    ));

                } while (cursor.moveToNext());
                cursor.close();

        }
        else{
            Log.d("DataBase Count", "Cursor:Empty ");
        }

    }
    private void initializeAdapter(){
        WriteUpsAdapter adapter = new WriteUpsAdapter(writeups);
        rv.setAdapter(adapter);
    }

}
