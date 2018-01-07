package com.linlif.socketchat;

/**
 * Created by lin on 2018/1/7.
 */
public class ChatDate {
    public String content;
    public String name;
    public long time;

    public ChatDate(String content, String name) {
        this.content = content;
        this.name = name;
        time = System.currentTimeMillis();
    }
}
