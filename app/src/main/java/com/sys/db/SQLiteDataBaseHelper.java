package com.sys.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LY on 2018/3/26.
 */

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

    private Context mContext ;
    private int version = 1 ;
    private String batabaseName = "message.db";

    public SQLiteDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name , factory, version);
        mContext = context ;
    }

    public SQLiteDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context ;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table message(_id integer primary key autoincrement , " +
                "type integer , content text , time text , showtime integer , sendname text , state integer)" ;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
