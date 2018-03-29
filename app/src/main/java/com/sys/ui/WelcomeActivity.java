package com.sys.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.sys.R;

/**
 * 启动界面
 * Created by LY on 2018/3/25.
 */

public class WelcomeActivity extends Activity {

    private Handler mHandler ;
    private int MESSAGETPYEO = 1001;
    private int MESSAGETPYET = 1002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == MESSAGETPYEO){
                    if(true){
                        startActivity( new Intent(WelcomeActivity.this,LoginActivity.class));
                    }else{
                        startActivity( new Intent(WelcomeActivity.this,HomeActivity.class));
                    }
                }else if(msg.what == MESSAGETPYET){
                    finish();
                }
            }
        };
        mHandler.sendEmptyMessageDelayed(MESSAGETPYEO,2000);
        mHandler.sendEmptyMessageDelayed(MESSAGETPYET,4000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MESSAGETPYEO);
        mHandler.removeMessages(MESSAGETPYET);
    }
}
