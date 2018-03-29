package com.sys.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sys.R;
import com.sys.adapter.ChatAdapter;
import com.sys.bend.ChatMessage;
import com.sys.bend.InputText;
import com.sys.bend.Message;
import com.sys.bend.Perception;
import com.sys.bend.RequestJson;
import com.sys.bend.UserInfo;
import com.sys.db.DBHelper;
import com.sys.tools.HttpUtils;
import com.sys.tools.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by LY on 2018/3/25.
 */

public class ChatFragment extends Fragment implements EditText.OnFocusChangeListener , TextWatcher ,View.OnClickListener{

    private long timeShow = 60000; // 超过这么多时间就显示时间（这里以毫秒为单位）

    private String URL = "http://openapi.tuling123.com/openapi/api/v2" ; //  http://openapi.tuling123.com/openapi/api/v2

    private String paa_key = "80ed79490a7e4984a72518733a97a031"; //
    private String secret_key = "c191bc1c0e9d7b44";

    private static String API_KEY = "534dc342ad15885dffc10d7b5f813451";
    private static String oldURL = "http://www.tuling123.com/openapi/api";
    private View mView ;
    private RecyclerView mRecyclerView;
    private EditText inputEt ;
    private TextView sendTv ;
    private ChatAdapter mAapter ;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Request mRequest ;
    private ImageView settingImg ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat_layout , container , false);
        findViewById(mView) ;
        return mView;
    }

    private void findViewById(View mView){
        mRecyclerView = mView.findViewById(R.id.recycleview);
        inputEt = mView.findViewById(R.id.input_et);
        sendTv = mView.findViewById(R.id.send_tv);
        settingImg = mView.findViewById(R.id.setting_img);
        inputEt.addTextChangedListener(this);
        sendTv.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAapter = new ChatAdapter(getContext());
        mRecyclerView.setAdapter(mAapter);
        initLister();
        intiData();
    }

    /**
     * 读取数据库的数据
     */
    private void intiData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Message> messages = (ArrayList<Message>) DBHelper.getInstance(getContext()).queryAll();
                if(messages != null ){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAapter.setDatas(messages);
                            ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mAapter.getItemCount()-1,0);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 设置监听器
     */
    private void initLister(){
        settingImg.setOnClickListener(this);
        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(bottom<oldBottom){
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.scrollToPosition(mAapter.getItemCount() -1);
                        }
                    },100);
                }
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(TextUtils.isEmpty(s.toString())){
            sendTv.setBackgroundResource(R.drawable.send_tv_nocan);
        }else {
            sendTv.setBackgroundResource(R.drawable.send_tv_can);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_tv :
                if(TextUtils.isEmpty(inputEt.getText().toString())){
                    Toast.makeText(getContext(),"发送的内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 网络请求
                Message message = new Message();
                message.setContent(inputEt.getText().toString());
                message.setType(0);
                message.setSendName(SharedPreferencesHelper.getInstance(getContext()).getName());
                Message m = DBHelper.getInstance(getContext()).queryLast();
                Long time = System.currentTimeMillis() ;
                if(m == null){
                    message.setShowtime(1);
                }else if(time - Long.parseLong(m.getTime())>  timeShow){
                    message.setShowtime(1);
                }
                message.setTime(String.valueOf(time)); // 系统当前时间
                DBHelper.getInstance(getContext()).insert(message);
                mAapter.setOneMessage(message);
                ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mAapter.getItemCount()-1,0);
                sendMessageGET(message);
                inputEt.setText("");
                break;
            case R.id.setting_img:
                startActivity( new Intent( getActivity() , SetUpActivity.class));
                break;
        }

    }

    /**
     * 发送消息
     */
    private void sendMessageGET( Message message ){
        String url = oldURL + "?key=" + API_KEY + "&info=" + message.getContent() ;
        Log.d("TAG111" , url);
        mRequest = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG333" , "请求异常");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("TAG222" , json);
                try {
                    JSONObject object = new JSONObject(json);
                    String text = object.getString("text");
                    final Message msg = new Message();
                    Message m = DBHelper.getInstance(getContext()).queryLast();
                    Long time = System.currentTimeMillis() ;
                    if(m == null){
                        m.setShowtime(1);
                    }else if(time - Long.parseLong(m.getTime()) >  timeShow){
                        m.setShowtime(1);
                    }
                    msg.setContent(text);
                    msg.setType(1);
                    msg.setTime(String.valueOf(System.currentTimeMillis())); // 系统当前时间
                    msg.setSendName("机器人");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAapter.setOneMessage(msg);
                            ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mAapter.getItemCount()-1,0);
                        }
                    });
                    DBHelper.getInstance(getContext()).insert(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 发送消息
     */
    private void sendMessage( Message message ){
        InputText inputText = new InputText(message.getContent());
        Perception perception = new Perception();
        perception.setmInputText(inputText);
        RequestJson requestJson = new RequestJson();
        requestJson.setmPerception(perception);
        Gson gson = new Gson();
        String json4 = gson.toJson(requestJson,RequestJson.class);
        Log.d("TAG111" , json4);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//"类型,字节码"
        RequestBody formBody = FormBody.create(mediaType , json4);
        mRequest = new Request.Builder().url(URL).post(formBody).build();
        okHttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG44444" , response.body().string());
            }
        });
    }

    public void sendMessage() {
        final String msg = inputEt.getText().toString();
        if (TextUtils.isEmpty(msg))
        {
            Toast.makeText(getContext(), "您还没有填写信息呢...", Toast.LENGTH_SHORT).show();
            return;
        }

        ChatMessage to = new ChatMessage(ChatMessage.Type.OUTPUT, msg);
        to.setDate(new Date());
//        mDatas.add(to);
//        mAdapter.notifyDataSetChanged();
//        mChatView.setSelection(mDatas.size() - 1);
//
//        mMsg.setText("");
        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        new Thread() {
            public void run() {
                ChatMessage from = null;
                try {
                    from = HttpUtils.sendMsg(msg);
                    Log.d("TAG333" , String.valueOf(from.getMsg()));
                } catch (Exception e) {
                    from = new ChatMessage(ChatMessage.Type.INPUT, "服务器挂了呢...");
                }
            };
        }.start();
    }
}
