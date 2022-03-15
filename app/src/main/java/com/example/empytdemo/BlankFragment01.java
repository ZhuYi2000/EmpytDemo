package com.example.empytdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


//调用Fragment的Activity继承的父类不能使用Activity，要继承 FragmentActivity 或者 AppCompatActivity


public class BlankFragment01 extends Fragment {

    private View root_view;
    private TextView textView;

    private IFragmentCallback fragmentCallback;
    public void setFragmentCallback(IFragmentCallback callback){
        fragmentCallback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //测试消息传递
        Bundle bundle = this.getArguments();//在这个类的任何位置都可以访问这个方法获取到传递来的信息
        String test_str = bundle.getString("testMessage");
        Log.e("zhu","bundle传递来的消息是："+test_str);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(root_view == null) {//渲染fragment的UI
            root_view = inflater.inflate(R.layout.fragment_blank01, container, false);
        }
        Log.e("zhu","创建blankFragment01");
        textView = root_view.findViewById(R.id.fragment01_textview);
        Button button = root_view.findViewById(R.id.fragment01_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("你好~");
            }
        });

        //
        Button button2 = root_view.findViewById(R.id.fragment01_btn_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentCallback.sendMsgToActivity("msg from fragment!");
                String text = fragmentCallback.getMsgFromActivity("useless");
                Toast.makeText(BlankFragment01.this.getContext(),text,Toast.LENGTH_SHORT).show();
            }
        });

        return root_view;
    }
}
/*
* Fragment生命周期
* 打开界面：onAttach() ->onCreate() -> onCreateView() -> onActivityCreate() -> onStart() -> onResume()
* 按下主屏键：onPause() -> onStop()
* 重新从任务管理器中打开应用：onStart() -> onResume()
* 按下后退键：onPause() -> onStop() -> onDestroyView() -> onDestroy() -> onDetach()
*
* 让新的view入栈时：onPause() -> onStop() -> onDestroyView()
* 旧的view出栈时：onCreateView() -> onActivityCreate() -> onStart() -> onResume()
*/