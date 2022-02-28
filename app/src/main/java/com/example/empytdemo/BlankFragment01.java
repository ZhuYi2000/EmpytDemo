package com.example.empytdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


//调用Fragment的Activity继承的父类不能使用Activity，要继承 FragmentActivity 或者 AppCompatActivity


public class BlankFragment01 extends Fragment {

    private View root_view;
    private TextView textView;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(root_view == null) {
            root_view = inflater.inflate(R.layout.fragment_blank01, container, false);
        }
        textView = root_view.findViewById(R.id.fragment01_textview);
        button = root_view.findViewById(R.id.fragment01_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("你好~");
            }
        });

        return root_view;
    }
}