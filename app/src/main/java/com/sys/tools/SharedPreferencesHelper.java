package com.sys.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LY on 2018/3/25.
 */

public class SharedPreferencesHelper {

    private static String FILEANME = "SIMPLESYSTEM";
    private Context mContext ;
    private static SharedPreferencesHelper mSharedPreferencesHelper ;
    private SharedPreferences mSharedPreferences ;
    private SharedPreferences.Editor mEditor;

    public static SharedPreferencesHelper getInstance(Context context){
        if(mSharedPreferencesHelper == null ){
            synchronized (SharedPreferencesHelper.class){
                if(mSharedPreferencesHelper == null ){
                    mSharedPreferencesHelper = new SharedPreferencesHelper(context);
                }
            }
        }
        return mSharedPreferencesHelper ;
    }

    /**
     *
     * @param context
     */
    private SharedPreferencesHelper(Context context){
        mContext = context ;
        mSharedPreferences = context.getSharedPreferences(FILEANME , Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * 保存用户名
     * @param name
     */
    public void setName(String name){
        mEditor.putString("name" ,name );
        mEditor.commit();
    }

    /**
     * 获取用户名
     * @return
     */
    public String getName(){
        return mSharedPreferences.getString("name" , "");
    }

    /**
     * 保存密码
     * @param password
     */
    public void setPassword(String password){
        mEditor.putString("password" ,password );
        mEditor.commit();
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassword(){
        return mSharedPreferences.getString("password" , "");
    }

}
