package com.gikonyo.safespace;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //now we create a table
    public static final String TABLE_NAME="COUNTRIES";

    //the table columns
    public static final String _ID="_id";
    public static final String SUBJECT="subject";
    public static final String DESC="description";

    //the database information
    static final String DB_NAME="SAFE_SPACE.DB";

    //now we specify the database version which we will update when we update the app
    static final int DB_VERSION=1;

    //queries
    private static final String CREATE_TABLE="create table "
            + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SUBJECT + " TEXT NOT NULL, "+ DESC + "TEXT);";

    //the constructor for the database helper
    public DatabaseHelper(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //query execution
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
