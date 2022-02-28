package com.example.empytdemo;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends BaseAdapter {

    private List<Fruit> fruits;//存放构造方法中传递过来的数据
    private Context context;

    public FruitAdapter(List<Fruit> fruits, Context context) {
        this.fruits = fruits;
        this.context = context;
    }

    //返回数据条目
    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    //返回item的id
    @Override
    public long getItemId(int i) {
        return i;
    }

    //返回每一个item的view并设置,i是position
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        //加载组件，将id为R.layout.list_item的组件加载到viewGroup布局中，并返回一个view
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
            viewHolder.textView = view.findViewById(R.id.myListItem);
            view.setTag(viewHolder);
            Log.e("zhu","一、getView "+i);
        }else{
            viewHolder = (ViewHolder) view.getTag();
            Log.e("zhu","避免重复加载 "+i);
            //避免重复加载view,也就是list_item,使得一开始加载的那几个view可以一直被复用
        }
        viewHolder.textView.setText(fruits.get(i).getName());

        return view;
    }

    //优化调用时间
    private final class ViewHolder{
        TextView textView;
    }
}
