package com.example.empytdemo;

public interface IFragmentCallback {
    void sendMsgToActivity(String msg);
    String getMsgFromActivity(String msg);
}
