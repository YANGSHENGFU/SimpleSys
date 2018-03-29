package com.sys.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LY on 2018/3/25.
 */

public class ToastHelper {

    private Context mContext ;

    public void makeText(String content ){
       Toast.makeText(mContext , content , Toast.LENGTH_SHORT).show();
    }

}
