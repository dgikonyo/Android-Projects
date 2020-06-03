package com.gikonyo.safespace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;


    //WE CREATE THE CONSTRUCTOR
    public DBManager(Context c){

        context=c;
    }

    public DBManager open() throws SQLException {
        dbHelper=new DatabaseHelper(context);//we are creating an instance and passing the context to it
        database=dbHelper.getWritableDatabase();

        return this;
    }

    public void close(){

        dbHelper.close();//we are closing the DatabaseHelper class

    }

    public void insert(String name, String desc){
        ContentValues contentValues=new ContentValues();//contentvalues pass the data from raw android synta to sql syntax
        contentValues.put(DatabaseHelper.SUBJECT,name);//so here we are passing the value of the SUBJECT to name
        contentValues.put(DatabaseHelper.DESC,desc);
        database.insert(DatabaseHelper.TABLE_NAME,null,contentValues);



    }

    public Cursor fetch(){//we are fetching the columns and making a new string array
        String [] columns=new String[]{DatabaseHelper._ID,DatabaseHelper.SUBJECT,
                DatabaseHelper.DESC};
        //the Cursor will help us to get to our result set

        Cursor cursor=database.query(DatabaseHelper.TABLE_NAME, columns,
                null, null,
                null,null,null);

        if(cursor!=null){//if the value of the cursor is not null, it will move tp the first record
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
         int i  =database.update(DatabaseHelper.TABLE_NAME,contentValues
         , DatabaseHelper._ID + " = "+ _id,null);

         return i;
    }

    public void delete(long _id){
        database.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper._ID
                +"="+ _id,null);
    }
}
