package com.codingstrings.sns.screens;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstrings.sns.utils.adapters.RVAdapter;
import com.codingstrings.sns.sql.SqlAssignments;
import com.codingstrings.sns.models.Assignment;
import com.codingstrings.sns.R;

import java.util.ArrayList;
import java.util.List;

public class AssignmentActivity extends Activity {


    private List<Assignment> assignments = null;
    private RecyclerView rv= null;
    private SQLiteDatabase readAble = null;
    private SqlAssignments sqLiteDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        sqLiteDatabase = new SqlAssignments(SplashScreenActivity.mGetContext());
        readAble       = sqLiteDatabase.getReadableDatabase();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();



    }

    private void initializeData(){

        assignments = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SqlAssignments.TABLE_NAME;
        Cursor cursor      = readAble.rawQuery(selectQuery, null);

        Log.d("DataBase Count", "initializeData: "+cursor.getCount());

        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
                do {

                    assignments.add(new Assignment(cursor.getString(0),
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
        RVAdapter adapter = new RVAdapter(assignments);
        rv.setAdapter(adapter);
    }

}
