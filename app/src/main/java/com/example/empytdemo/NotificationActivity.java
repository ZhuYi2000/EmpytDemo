package com.example.empytdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private List<Fruit> fruits = new ArrayList<>();  //数据集

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);
        Log.e("zhu","onCreate：进入NotificationActivity");

        //生成数据
        for(int i = 0;i < 100;i++){
            Fruit fruit = new Fruit("水果"+i);
            fruits.add(fruit);
        }

        //获取listview并设置adapter
        ListView listView = findViewById(R.id.myListView);
        listView.setAdapter(new FruitAdapter(fruits,this));


        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e("zhu","点击:"+position);
                Log.e("zhu","点击:"+id);
            }
        });


    }
}
