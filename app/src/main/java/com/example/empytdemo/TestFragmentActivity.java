package com.example.empytdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//调用Fragment的Activity继承的父类不能使用Activity，要继承 FragmentActivity 或者 AppCompatActivity
public class TestFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        Button button01 = findViewById(R.id.test_fragment_btn1);
        button01.setOnClickListener(this);

        Button button02 = findViewById(R.id.test_fragment_btn2);
        button02.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test_fragment_btn1:
                //以bundle的形式传递信息
                Bundle bundle = new Bundle();
                bundle.putString("testMessage","Activity到Fragment的信息传递");

                BlankFragment01 bf01 = new BlankFragment01();
                bf01.setArguments(bundle);//传递到fragment中

                replaceFragment(bf01);//再动态创建这个fragment
                break;
            case R.id.test_fragment_btn2:
                replaceFragment(new BlankFragment02());
                break;
        }
    }

    /*
    * 1.创建一个待处理的fragment
    * 2.获取FragmentManager，一般通过getSupportFragmentManager()
    * 3.开启一个事务transaction，一般调用FragmentManager的beginTransaction();
    * 4.使用transaction进行fragment的替换（可以选择入栈）
    * 5.提交事务 commit
    * */

    //replace方法,动态切换fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();//通过manager获取触发器
        transaction.replace(R.id.test_fragment_framelayout,fragment);
        transaction.addToBackStack(null);  //将fragment入栈，在用户点击返回键时，逐个出栈
        transaction.commit();
    }
}