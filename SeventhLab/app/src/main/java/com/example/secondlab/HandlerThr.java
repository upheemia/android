package com.example.secondlab;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.secondlab.db.DatabaseHandler;
import com.example.secondlab.db.User;

public class HandlerThr {
    Handler t_handler;
    final Message message = Message.obtain();
    Context context;

    HandlerThr(Handler handler, Context context){
        this.t_handler = handler;
        this.context = context;
    }

    DatabaseHandler db;

    public void addUser(User user){
        new Thread(() -> {
            Log.i("Thread", Thread.currentThread().getName());
            db = new DatabaseHandler(context);
            String messageAdd = db.addUser(user);
            message.sendingUid = 1;
            message.obj = messageAdd;
            t_handler.sendMessage(message);
        }).start();
    }
    public void getUser(String log){
        new Thread(() -> {
            Log.i("Thread", Thread.currentThread().getName());
            db = new DatabaseHandler(context);
            String loginData = db.getUser(log);

            message.sendingUid = 2;
            message.obj = loginData;
            t_handler.sendMessage(message);
        }).start();
    }
    public void updPass(String log, String pass){
        new Thread(() -> {
            Log.i("Thread", Thread.currentThread().getName());
            db = new DatabaseHandler(context);
            String messageUpd = db.updatePassword(log, pass);
            message.sendingUid = 3;
            message.obj = messageUpd;
            t_handler.sendMessage(message);
        }).start();
    }
    public void delUser(String log){
        new Thread(() -> {
            Log.i("Thread", Thread.currentThread().getName());
            db = new DatabaseHandler(context);
            String messageUpd = db.deleteUser(log);
            message.sendingUid = 4;
            message.obj = messageUpd;
            t_handler.sendMessage(message);
        }).start();
    }
}
