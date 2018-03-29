package com.sys.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sys.R;
import com.sys.tools.SharedPreferencesHelper;

/**
 * Created by LY on 2018/3/25.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText mNameEt , mPasswordEt ;
    private TextView registerTv;

    private SharedPreferencesHelper spHelper ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        initData();
        findViewById();
    }

    private void initData(){
        spHelper = SharedPreferencesHelper.getInstance(this);
    }

    /**
     *
     */
    private void findViewById(){
        mNameEt = findViewById(R.id.name_et);
        mPasswordEt = findViewById(R.id.password_et);
        registerTv = findViewById(R.id.register_tv);
        registerTv.setOnClickListener(this);
        mNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>6){
                    Toast.makeText(RegisterActivity.this , "用户名不能高于4位" , Toast.LENGTH_SHORT).show();
                    mNameEt.setText(s.subSequence(0,6));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>11){
                    Toast.makeText(RegisterActivity.this , "密码不能高于4位" , Toast.LENGTH_SHORT).show();
                    mPasswordEt.setText(s.subSequence(0,11));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_tv:
                if(mNameEt.getText().toString().length()<3){
                    Toast.makeText(this , "用户名不能低于3位" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPasswordEt.getText().toString().length()<6){
                    Toast.makeText(this , "密码不能低于6位" , Toast.LENGTH_SHORT).show();
                    return;
                }
                spHelper.setName(mNameEt.getText().toString());
                spHelper.setPassword(mPasswordEt.getText().toString());
                Toast.makeText(this , "注册成功" , Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
