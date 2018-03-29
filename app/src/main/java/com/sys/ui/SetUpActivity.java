package com.sys.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sys.R;
import com.sys.db.DBHelper;

/**
 * Created by LY on 2018/3/28.
 */

public class SetUpActivity extends Activity implements View.OnClickListener{

    private ImageView backImage ;
    private RelativeLayout emptyChatRecords ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_layout);
        findViewById() ;
    }

    private void findViewById(){
        backImage = findViewById(R.id.back_action);
        emptyChatRecords = findViewById(R.id.empty_chat_records);
        backImage.setOnClickListener(this);
        emptyChatRecords.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action :
                finish();
                break;
            case R.id.empty_chat_records :
                DBHelper.getInstance(this).deleteAll();
                Toast.makeText(this , "数据删除成功" , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
