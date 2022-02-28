package com.example.empytdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class Chrome01 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
