package com.sys.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.sys.Interface.SwithchViewStatus;
import com.sys.R;
import com.sys.db.DBHelper;
import com.sys.dialog.EmptyChatDialog;
import com.sys.tools.SharedPreferencesHelper;
import com.sys.view.SwitchView;

/**
 * Created by LY on 2018/3/28.
 */

public class SetUpActivity extends Activity implements View.OnClickListener ,SwithchViewStatus {

    private ImageView backImage ;
    private RelativeLayout emptyChatRecords ;
    private RelativeLayout resetPassword ;
    private RelativeLayout loginOut;
    private SwitchView mSVLogin ;
    private SwitchView mSVpassword ;
    private View mDialogView ;
    private EmptyChatDialog mDialog ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_layout);
        findViewById() ;
        initData();
        initDialog() ;
    }

    private void findViewById(){
        backImage = findViewById(R.id.back_action);
        emptyChatRecords = findViewById(R.id.empty_chat_records);
        mSVpassword = findViewById(R.id.password_sv);
        mSVLogin = findViewById(R.id.login_sv);
        resetPassword = findViewById(R.id.reset_password);
        loginOut = findViewById(R.id.login_out);
        backImage.setOnClickListener(this);
        emptyChatRecords.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        loginOut.setOnClickListener(this);
    }

    private void initData(){
        mSVLogin.setIsOpen(SharedPreferencesHelper.getInstance(this).getIsLogonFree());
        mSVpassword.setIsOpen(SharedPreferencesHelper.getInstance(this).getRememberPassword());
        mSVLogin.setmSwithchViewStatus(this);
        mSVpassword.setmSwithchViewStatus(this);
    }

    /**
     * 初始化dialog
     */
    private void initDialog(){
        mDialog = new EmptyChatDialog(this);
        mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_empty_chat_layout , null);
        mDialog.setmView(mDialogView);

        // 取消
        mDialogView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDialog.isShowing()){
                    mDialog.dismiss();
                }
            }
        });
        // 删除
        mDialogView.findViewById(R.id.delet_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.getInstance(SetUpActivity.this).deleteAll();
                mDialog.dismiss();
                Toast.makeText(SetUpActivity.this , "数据删除成功" , Toast.LENGTH_SHORT).show();
                ChatFragment.mHandler.sendEmptyMessage(ChatFragment.EMPTYCHAT);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action :
                finish();
                break;
            case R.id.empty_chat_records :
                if(mDialog.isShowing()){
                    mDialog.dismiss();
                }else{
                    mDialog.show();
                }
                break;
            case R.id.reset_password:
                break;
            case R.id.login_out:
                ChatFragment.mHandler.sendEmptyMessage(ChatFragment.FINSHACTION);
                finish();
                break;
        }
    }

    @Override
    public void swithchViewStatus(View v , int b) {
        switch (v.getId()){
            case R.id.login_sv :
                if(b == 0){
                    SharedPreferencesHelper.getInstance(this).setIsLogonFree(false);
                }else{
                    SharedPreferencesHelper.getInstance(this).setIsLogonFree(true);
                }
                break;
            case R.id.password_sv :
                if(b == 0){
                    SharedPreferencesHelper.getInstance(this).setRememberPassword(false);
                }else{
                    SharedPreferencesHelper.getInstance(this).setRememberPassword(true);
                }
                break;
        }
    }
}
