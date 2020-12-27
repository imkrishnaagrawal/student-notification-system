package com.codingstrings.sns.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 10/12/17.
 */

public class SqlWriteUps extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "writeups";
    public static final String TABLE_NAME = "writeups";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_SEEN = "seen";
    public static final String COLUMN_GROUP = "grp";


    public SqlWriteUps(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SUBJECT + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_SEEN + " TEXT, " +
                COLUMN_GROUP + " TEXT )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
