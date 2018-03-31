
package com.sys.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sys.R;
import com.sys.tools.SharedPreferencesHelper;

/**
 * Created by LY on 2018/3/31.
 */

public class ResetPasswordActivity extends Activity implements View.OnClickListener{

    private EditText nameEt , passwordEt , newPasswordEt;
    private TextView loginTv ;
    private ImageView backImage ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_layout);
        findViewById();
    }

    private void findViewById(){
        nameEt = findViewById(R.id.name_et);
        passwordEt = findViewById(R.id.password_et);
        newPasswordEt = findViewById(R.id.new_password_et);
        loginTv = findViewById(R.id.login_tv);
        backImage = findViewById(R.id.back_action);
        loginTv.setOnClickListener(this);
        backImage.setOnClickListener(this);
        newPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>11){
                    Toast.makeText(ResetPasswordActivity.this , "密码不能高于11位" , Toast.LENGTH_SHORT).show();
                    newPasswordEt.setText(s.subSequence(0,11));
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
            case R.id.login_tv:
                if(TextUtils.isEmpty(nameEt.getText().toString())){
                    Toast.makeText(this , "请输入用户名" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordEt.getText().toString())){
                    Toast.makeText(this , "请输入密码" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(newPasswordEt.getText().toString())){
                    Toast.makeText(this , "请输入新密码" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!SharedPreferencesHelper.getInstance(this).getName().equals(nameEt.getText().toString())){
                    Toast.makeText(this , "用户名不存在" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!SharedPreferencesHelper.getInstance(this).getPassword().equals(passwordEt.getText().toString())){
                    Toast.makeText(this , "密码不正确" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPasswordEt.getText().toString().length()<6){
                    Toast.makeText(this , "密码不能低于6位" , Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferencesHelper.getInstance(this).setPassword(newPasswordEt.getText().toString());
                Toast.makeText(this , "密码重置成功" , Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.back_action:
                finish();
                break;
        }
    }
}
