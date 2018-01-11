package com.linlif.socketchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.linlif.socketchat.client.SocketThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lin on 2018/1/7.
 */
public class ChatActivity extends AppCompatActivity implements ChatView {

    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.etContent)
    EditText etContent;
    private String host;
    private String prot;
    private ChatAdapter mAdapter;
    private SocketThread socketThread;

    private String userId = String.valueOf(System.currentTimeMillis());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        host = intent.getStringExtra("host");
        prot = intent.getStringExtra("prot");
        List<ChatDate> mList = new ArrayList<>();
        mAdapter = new ChatAdapter(this, userId,mList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(mAdapter);
        Log.e("test" , mAdapter.hashCode()+"has");


        socketThread = new SocketThread(this);
        socketThread.start();
    }

    @OnClick(R.id.btnSend)
    public void onClick(View view) {
        String msg = etContent.getText().toString();
        etContent.setText(null);
        socketThread.sendMsg(msg);
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getProt() {
        return prot;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void showDiaolg(String msg) {

    }

    @Override
    public void receiveMsg(ChatDate bean) {
        mAdapter.addChatDate(bean);
    }
}
