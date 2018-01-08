package com.linlif.socketchat;

/**
 * Created by linlif on 2018/1/8.
 */

public interface ChatView {

    String getHost();

    String getProt();

    String getUserId();

    void showDiaolg(String msg);

    void receiveMsg(ChatDate bean);
}
