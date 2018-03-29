package com.sys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sys.R;

/**
 * Created by LY on 2018/3/30.
 */

public class EmptyChatDialog extends Dialog {

    private View mView ;

    public EmptyChatDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    public EmptyChatDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EmptyChatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
    }

    public void setmView(View mView) {
        this.mView = mView;
    }
}
