package com.example.empytdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private ProgressBar horizontalPB;

    private NotificationManager manager;
    //是状态栏通知的管理类，负责发通知、清楚通知等。
    //是一个系统Service，必须通过 getSystemService()方法来获取。
    private Notification notification;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Stetho.initializeWithDefaults(this);//  chrome调试数据库

        TextView nameOfGame = findViewById(R.id.nameOfTest);  // 通过ID获取控件
        //nameOfGame.setText("宝可梦——GO");

        //进度条
        horizontalPB = findViewById(R.id.horizontalPB);


        //匿名内部类来获取button的点击事件
        Button testButton = findViewById(R.id.testButton);
        //点击事件
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"点击并将进度条重置为30%", Toast.LENGTH_SHORT).show();
                horizontalPB.setProgress(30);
            }
        });


        //获取editText内容
        Button etButton = findViewById(R.id.ETbutton);
        et = findViewById(R.id.testEditText);
        etButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString();
                Log.e("zhu","输入的内容:"+text);
                Toast.makeText(getApplicationContext(),"输入的内容:"+text, Toast.LENGTH_SHORT).show();
            }
        });


        //点击图片
        ImageView testImage = findViewById(R.id.testImage);
        testImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"I LOVE YOU TOO~", Toast.LENGTH_SHORT).show();
                //通过加载xml动画设置文件来创建一个animation对象
                Animation animation_alpha = AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha);
                testImage.startAnimation(animation_alpha);
            }
        });





        //状态栏通知
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //1.设置channel
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("zhu", "宝可梦GO",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        //2.设置跳转页面,别忘了在AndroidManifest.xml里面注册新的activity
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        // PendingIntent可以看作是对Intent的一个封装，但它不是立刻执行某个行为
        // 而是满足某些条件或触发某些事件后才执行指定的行为。


        notification = new NotificationCompat.Builder(this,"zhu")
                .setContentTitle("测试标题——宝可梦GO")     //标题、内容、小图标(必须为RGB)
                .setContentText("学习Android需要科学的路径")
                .setSmallIcon(R.drawable.ic_baseline_person_24)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)    //点击后自动关闭
                .setWhen(System.currentTimeMillis())
                .build();


        //toolbar中返回键设置
        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"事件:点击返回键", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //checkbox反射而来的事件
    public void clickSprinkles(View view) {
        CheckBox checkBox = (CheckBox) view;
        String message;
        if (checkBox.isChecked()) {
            message = "Thanks for selecting sprinkles.";
        } else {
            message = "Thanks for not selecting sprinkles.";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void clickCherry(View view) {
        CheckBox checkBox = (CheckBox) view;
        int resourceId;
        if (checkBox.isChecked()) {
            resourceId = R.string.cherry;
        } else {
            resourceId = R.string.no_cherry;
        }
        checkBox.setText(getString(resourceId));
    }


    //进度条processBar 反射的事件
    public void clickPB(View view){
        int progress = horizontalPB.getProgress();
        progress += 10;
        horizontalPB.setProgress(progress);
    }

    //发送通知
    public void sendNotification(View view){
        manager.notify(1,notification);
    }

    public void cancelNotification(View view){
        manager.cancel(1);//前后ID要一致
    }

    //对话框
    public void sendAlertDialog(View view){
        //创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // create() show()方法的位置不能变
        builder.setIcon(R.drawable.icon)
                .setTitle("欢迎加入宝可梦GO 训练师")
                .setMessage("你需要在游戏世界中捕获宝可梦并训练它们。而游戏世界的地图是现实世界的整个地球，这酷极了，不是吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"事件:点击确定", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"事件:点击取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"事件:点击继续", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }

    //下拉弹窗
    public void sendPopupWindow(View view){
        View popupView = getLayoutInflater().inflate(R.layout.popup_layout,null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);//设置下拉框内容及格式，是否获取焦点
        popupWindow.showAsDropDown(view);//显示


        Button btn1 = popupView.findViewById(R.id.popupButton1);
        Button btn2 = popupView.findViewById(R.id.popupButton2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"事件:点击上海", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"事件:点击北京", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    //页面跳转
    public void openTestSQL(View view) {
        //启动新的页面
        Intent sql_intent = new Intent(this,TestSQLActivity.class);
        startActivity(sql_intent);
        Log.e("zhu","点击sql按钮");
    }

    //点击事件
    public void testGet(View view) throws IOException {
        //必须在子线程中使用
        new Thread(){
            @Override
            public void run() {
                try {
                    long  startTime = System.currentTimeMillis();

                    int number = 100;//获取ID为1-100的宝可梦的名字（顺序排列）
                    String res = getUrl("https://pokeapi.co/api/v2/pokemon/?limit="+number);
                    //Log.e("zhu",res);

                    long  middleTime = System.currentTimeMillis();

                    JSONObject res_json = new JSONObject(res);
                    JSONArray raw_list = res_json.getJSONArray("results");
                    List<String> p_name_list = new ArrayList<>();
                    for(int i = 0; i < number; i++){
                        JSONObject temp = raw_list.getJSONObject(i);
                        String p_name = temp.get("name").toString();
                        p_name_list.add(p_name);
                        Log.e("zhu",p_name);
                    }
                    Log.e("zhu","长度为："+p_name_list.size());

                    long  endTime = System.currentTimeMillis();
                    Log.e("zhu","geturl时间"+(middleTime-startTime)+"ms");
                    Log.e("zhu","总时间"+(endTime-startTime)+"ms");
/*  geturl时间2850ms
    总时间2860ms,基本上json的处理不耗时,主要需提高网络请求的性能（高并发）
*/
                    //String p_name = res_json.get("name").toString();

                }catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //同步GET方法
    public String getUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        //创建一个request对象
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        //获取响应并把响应体返回
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

/*
* 官方插图，将后方的ID切换即可获取不同宝可梦的图片
* https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png
*
* */