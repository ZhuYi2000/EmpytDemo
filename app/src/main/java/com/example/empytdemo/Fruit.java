package com.example.empytdemo;

/* 列表数据的显示需要4个元素，分别是：
（1）用来展示列表的ListView;
（2）用来把数据映射到ListView上的Adapter
（3）需要展示的数据集
（4）数据展示的View模板
*/

public class Fruit {

    String name;//水果名称

    public Fruit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
