package com.linlif.socketchat.service;

import com.google.gson.Gson;
import com.linlif.socketchat.ChatDate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by linlif on 2018/1/8.
 */

public class StartService {

    private static final int PORT = 9999;
    private static List<Socket> mList = new ArrayList<Socket>();
    private static ServerSocket server = null;
    private static ExecutorService mExecutorService = null; //thread pool

    public static void initService(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(PORT);
                    mExecutorService = Executors.newCachedThreadPool();  //create a thread pool
                    System.out.println("服务器已启动...");
                    Socket client = null;
                    while(true) {
                        client = server.accept();
                        //把客户端放入客户端集合中
                        mList.add(client);
                        mExecutorService.execute(new Service(client)); //start a new thread to handle the connection
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    static class Service implements Runnable {
        private Socket socket;
        private BufferedReader in = null;
        private String msg = "";

        public Service(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //客户端只要一连到服务器，便向客户端发送下面的信息。
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public byte[] readStream(InputStream inStream) throws Exception {
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            return outSteam.toByteArray();
        }

        @Override
        public void run() {
            try {
                while(true) {
                    if((msg = in.readLine())!= null) {
                        System.out.println("接收:"+msg);
                        ChatDate bean =  new Gson().fromJson(msg, ChatDate.class);
                        //当客户端发送的信息为：exit时，关闭连接
                        if(bean.content.equals("exit")) {
                            System.out.println("用戶:"+bean.name+"已退出谈论组");
                            mList.remove(socket);
                            in.close();
                            socket.close();
                            this.sendmsg();
                            break;
                            //接收客户端发过来的信息msg，然后发送给客户端。
                        } else {
                            this.sendmsg();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**
         * 循环遍历客户端集合，给每个客户端都发送信息。
         * 可以使用观察者模式设计讨论组
         */
        public void sendmsg() {
            System.out.println(msg);
            int num =mList.size();
            for (int index = 0; index < num; index ++) {
                Socket mSocket = mList.get(index);
                PrintWriter pout = null;
                try {
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream())),true);
                    pout.println(msg);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
