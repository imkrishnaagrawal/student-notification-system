package com.codingstrings.sns.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 10/12/17.
 */

public class SqlToken extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "usertoken";
    public static final String TABLE_NAME = "mtoken";

    public static final String COLUMN_TOKEN = "token";

    public static final String COLUMN_ID = "id";


    public SqlToken(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "( " +
                COLUMN_ID + " INTEGER PRIMARY KEY , " +
                COLUMN_TOKEN + " TEXT NOT NULL ) " );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
