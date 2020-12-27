package com.codingstrings.sns.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 10/12/17.
 */

public class SqlNotice extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "notice";
    public static final String TABLE_NAME = "notice";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECT = "msubject";
    public static final String COLUMN_SEEN = "seen";
    public static final String COLUMN_MESSAGE = "message";


    public SqlNotice(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_SUBJECT + " TEXT, " +
                COLUMN_MESSAGE + " TEXT, " +
                COLUMN_SEEN + " TEXT  )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
