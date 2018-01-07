package com.linlif.socketchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lin on 2018/1/7.
 */
public class ChatAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<ChatDate> mList;
    private Context mContext;
    private String userId;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ChatAdapter(Context context, String userId) {
        this.mContext = context;
        this.userId = userId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ChatDate bean = mList.get(position);
        if (position == 0 || position > 0 && bean.time - mList.get(position - 1).time > 60000) {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(format.format(bean.time));
        } else {
            holder.tvTime.setVisibility(View.GONE);
        }
        holder.frameLeft.setVisibility(View.GONE);
        holder.frameRight.setVisibility(View.GONE);
        if (bean.content.equals("exit")) {
            holder.tvTip.setVisibility(View.VISIBLE);
            holder.tvTip.setText(bean.name.equals(userId) ? "您已退出讨论组" : bean.name + "已退出讨论组");
        } else if (bean.content.equals("join")) {
            holder.tvTip.setVisibility(View.VISIBLE);
            holder.tvTip.setText(bean.name.equals(userId) ? "您已加入讨论组" : bean.name + "加入讨论组");
        } else {
            holder.tvTip.setVisibility(View.GONE);
            holder.frameLeft.setVisibility(bean.name.equals(userId) ? View.GONE : View.VISIBLE);
            holder.frameRight.setVisibility(bean.name.equals(userId) ? View.VISIBLE : View.GONE);
            if (bean.name.equals(userId)) {
                holder.tvRight.setText(bean.content);
            } else {
                holder.tvLeft.setText(bean.content);
            }
        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
}

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvLeft)
        TextView tvLeft;
        @Bind(R.id.tvRight)
        TextView tvRight;
        @Bind(R.id.tvTip)
        TextView tvTip;
        @Bind(R.id.frameLeft)
        FrameLayout frameLeft;
        @Bind(R.id.frameRight)
        FrameLayout frameRight;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


