package com.linlif.socketchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lin on 2018/1/7.
 */
public class ChatActivity extends AppCompatActivity{

    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.etContent)
    EditText etContent;
    private String host;
    private String prot;
    private ChatAdapter mAdapter;

    private String userId = String.valueOf(System.currentTimeMillis());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        host = intent.getStringExtra("host");
        prot = intent.getStringExtra("prot");

        mAdapter = new ChatAdapter(this, userId);
        recycleView.setAdapter(mAdapter);
    }
}
