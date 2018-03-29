package com.sys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sys.R;
import com.sys.bend.Message;
import com.sys.tools.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LY on 2018/3/26.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseViewHolder> {

    private Context mContent;
    private List<Message> datas = new ArrayList<>();

    public ChatAdapter(Context context){
        mContent = context ;
    }

    public void setDatas(ArrayList<Message> list){
        if(datas.size() >0){
            ArrayList<Message> s = new ArrayList<>();
            s.addAll(datas);
            datas.clear();
            datas.addAll(list);
            datas.addAll(s);
        }else{
            datas.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加一条数据
     * @param message
     */
    public void setOneMessage( Message message ){
        datas.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null ;
        if(viewType == 0){
            view = LayoutInflater.from(mContent).inflate(R.layout.itme_recycle_msg_right_layout,parent,false);
            return new ChatRightViewHolder(view);
        }else{
            view = LayoutInflater.from(mContent).inflate(R.layout.itme_recycle_msg_left_layout,parent,false);
            return new ChatLeftViewHolder(view);
        }
    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.dateTv.setTag(0);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder , datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 绑定数据
     * @param holder
     * @param message
     */
    private void bindData(BaseViewHolder holder , Message message ){
        holder.dateTv.setTag(message.getTime());
        holder.dateTv.setText(message.getTime());
        holder.contentTv.setText(message.getContent());
        if(message.getShowtime() == 1 && holder.dateTv.getTag() == message.getTime()){
            holder.dateTv.setText(TimeUtil.timeShow(Long.parseLong(message.getTime())));
            holder.dateTv.setVisibility(View.VISIBLE);
        }else{
            holder.dateTv.setVisibility(View.GONE);
        }
    }

    /**
     *基类
     */
    class BaseViewHolder extends RecyclerView.ViewHolder{
        public int TAG = 2018 ;
        protected ImageView headImag;
        protected TextView dateTv , contentTv ;
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 左边布局
     */
    class ChatLeftViewHolder extends BaseViewHolder{
        public ChatLeftViewHolder(View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date_tv);
            headImag = itemView.findViewById(R.id.head_img);
            contentTv = itemView.findViewById(R.id.content_msg_tv);
        }
    }

    /**
     * 右边布局
     */
    class ChatRightViewHolder extends BaseViewHolder{
        public ChatRightViewHolder(View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date_tv);
            headImag = itemView.findViewById(R.id.head_img);
            contentTv = itemView.findViewById(R.id.content_msg_tv);
        }
    }
}
