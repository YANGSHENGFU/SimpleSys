package com.sys.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sys.R;
import com.sys.tools.SharedPreferencesHelper;

/**
 * Created by LY on 2018/3/25.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText mNameEt , mPasswordEt ;
    private TextView mRegisterTv,mLoginTv ;
    private SharedPreferencesHelper spHelper ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        initData();
        findViewById();
    }

    private void initData(){
        spHelper = SharedPreferencesHelper.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNameEt.setText(spHelper.getName());
        if(spHelper.getRememberPassword()){
            mPasswordEt.setText(spHelper.getPassword());
        }else{
            mPasswordEt.setText("");
        }

    }

    /**
     *
     */
    private void findViewById(){
        mNameEt = findViewById(R.id.name_et);
        mPasswordEt = findViewById(R.id.password_et);
        mRegisterTv = findViewById(R.id.register_tv);
        mLoginTv = findViewById(R.id.login_tv);
        mRegisterTv.setOnClickListener(this);
        mLoginTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_tv:
                if (TextUtils.isEmpty(mNameEt.getText().toString())){
                    Toast.makeText(LoginActivity.this , "请输入用户名" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mPasswordEt.getText().toString())){
                    Toast.makeText(LoginActivity.this , "请输入密码" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!spHelper.getName().equals(mNameEt.getText().toString())){
                    Toast.makeText(LoginActivity.this , "用户名不存在" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!spHelper.getPassword().equals(mPasswordEt.getText().toString())){
                    Toast.makeText(LoginActivity.this , "用户名和密码不匹配" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity( new Intent( this, HomeActivity.class));
                break;
            case R.id.register_tv:
                startActivity( new Intent( this, RegisterActivity.class));
                break;
        }
    }
}
