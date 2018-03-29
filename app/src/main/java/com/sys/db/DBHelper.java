package com.sys.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.sys.bend.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LY on 2018/3/26.
 */

public class DBHelper {

    private String batabaseName = "message.db";
    private String tableName = "message" ;
    private String _id = "_id";
    private String type = "type";
    private String content = "content";
    private String time = "time";
    private String showtime = "showtime";
    private String sendname = "sendname";

    private Context mContext ;
    private static DBHelper mDBHelper ;
    private static SQLiteDataBaseHelper mSQLiteDataBaseHelper ;

    public static DBHelper getInstance(Context context ){
        if(mDBHelper == null ){
            synchronized (DBHelper.class){
                if(mDBHelper == null){
                    mDBHelper = new DBHelper(context);
                }
            }
        }
        return mDBHelper ;
    }

    private DBHelper(Context context){
        mContext = context ;
        mSQLiteDataBaseHelper = new SQLiteDataBaseHelper(context ,getMyDatabaseName(context ,batabaseName) ,null , 1);
    }

    /**
     * 插入数据
     * @param message
     */
    public void insert(Message message){
        ContentValues cv = new ContentValues();
        cv.put(type ,message.getType());
        cv.put(content ,message.getContent());
        cv.put(time ,message.getTime());
        cv.put(showtime ,message.getShowtime());
        cv.put(sendname ,message.getSendName());
        mSQLiteDataBaseHelper.getWritableDatabase().insert(tableName ,null , cv);
        cv.clear();
    }

    /**
     * 删除数据
     * @param message
     */
    public void delete(Message message){

    }

    /**
     * 删除数据
     */
    public void deleteAll(){
        mSQLiteDataBaseHelper.getWritableDatabase().delete(tableName ,null ,null );
    }

    /**
     * 更新数据
     * @param message
     */
    public void update(Message message){

    }

    /**
     * 查找所有数据数据
     */
    public List<Message> queryAll(){
        SQLiteDatabase db = mSQLiteDataBaseHelper.getReadableDatabase() ;
        Cursor cursor = db.query(tableName , null ,null ,null ,null , null ,null );
        if( cursor == null || cursor.getCount() == 0 ){
            return null ;
        }
        List<Message> messages = new ArrayList<>();
        while (cursor.moveToNext()){
            Message message = new Message();
            message.setType(cursor.getInt(cursor.getColumnIndex(type)));
            message.setContent(cursor.getString(cursor.getColumnIndex(content)));
            message.setTime(cursor.getString(cursor.getColumnIndex(time)));
            message.setSendName(cursor.getString(cursor.getColumnIndex(sendname)));
            message.setShowtime(cursor.getInt(cursor.getColumnIndex(showtime)));
            messages.add(message);
        }
        cursor.close();
        return messages;
    }

    /**
     * 查找最后一条
     */
    public Message queryLast(){
        SQLiteDatabase db = mSQLiteDataBaseHelper.getReadableDatabase() ;
        Cursor cursor = db.query(tableName , null ,null ,null ,null , null ,null );
        if(cursor == null || cursor.getCount() == 0){
            return  null ;
        }
        cursor.moveToLast();
        Message message = new Message();
        message.setType(cursor.getInt(cursor.getColumnIndex(type)));
        message.setContent(cursor.getString(cursor.getColumnIndex(content)));
        message.setTime(cursor.getString(cursor.getColumnIndex(time)));
        message.setSendName(cursor.getString(cursor.getColumnIndex(sendname)));
        message.setShowtime(cursor.getInt(cursor.getColumnIndex(showtime)));
        return message ;
    }

    /**
     * 数据库保存的路径
     * @param context
     * @return
     */
    private String getMyDatabaseName( Context context , String  batabaseName){
        boolean isSdcardEnable =false;
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){//SDCard是否插入
            isSdcardEnable = true;
        }
        String dbPath = null;
        if(isSdcardEnable){
            dbPath =Environment.getExternalStorageDirectory().getPath() +"/Simple/";
        }else{//未插入SDCard，建在内存中
            dbPath =context.getFilesDir().getPath() + "/Simple/";
        }
        File dbp = new File(dbPath);
        if(!dbp.exists()){
            dbp.mkdirs();
        }
        batabaseName = dbPath + batabaseName;
        return batabaseName;
    }
}
