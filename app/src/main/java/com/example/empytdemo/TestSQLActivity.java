package com.example.empytdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class TestSQLActivity extends FragmentActivity {

    private EditText student_id;
    private EditText student_name;
    private EditText student_class;
    private RadioGroup student_sex;
    private RadioButton checked_sex;
    private boolean radio_checked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testsql_main);
        Log.e("zhu","onCreate：进入TestSQLActivity");

        final DatabaseHelper databaseHelper = new DatabaseHelper(this,"test_db",null,1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        student_name = findViewById(R.id.student_name);
        student_id = findViewById(R.id.student_id);
        student_class = findViewById(R.id.student_class);
        student_sex = findViewById(R.id.student_sex);

        //单选框设置
        student_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radio_checked = true;
                checked_sex = (RadioButton) findViewById(checkedId);
             }
        });

        //增加信息，待完善约束条件
        Button student_add = findViewById(R.id.button_add);
        student_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("zhu","onClick：点击add");
                if(radio_checked){
                    String sex = checked_sex.getText().toString();
                    //Toast.makeText(getApplicationContext(),"性别:"+checked_sex.getText(), Toast.LENGTH_SHORT).show();

                    ContentValues contentValues = new ContentValues();
                    String id = student_id.getText().toString().trim();
                    String name = student_name.getText().toString().trim();
                    String class_0 = student_class.getText().toString().trim();
                    // Java String类 trim() 方法用于删除字符串的头尾空白符。

                    contentValues.put("student_id",id);
                    contentValues.put("student_name",name);
                    contentValues.put("student_sex",sex);
                    contentValues.put("student_class",class_0);

                    db.insert("test_student",null,contentValues);
                    Toast.makeText(getApplicationContext(),"添加成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
// C:\Users\17396\AppData\Local\Google\AndroidStudio2020.3\device-explorer\xiaomi-m2102j2sc-3f0ccf59\data\data\com.example.empytdemo\databases

        //查询表内所有数据，并在log cat中显示，待完善至一个单独的listview界面
        Button student_select = findViewById(R.id.button_select);
        student_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("zhu","onClick：点击select");
                /*
                Cursor cursor = db.query("test_student", new String[]{"student_id","student_name",
                                "student_sex","student_class"}, "id=?", new String[]{"1"},
                        null, null, null, null);*/
                Cursor cursor = db.rawQuery("SELECT * FROM test_student",null);
                while(cursor.moveToNext()){
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String sex = cursor.getString(2);
                    String class_0 = cursor.getString(3);
                    Log.e("zhu","查询结果："+id+","+name+","+sex+","+class_0);
                }

                cursor.close();

            }
        });

        //根据ID删除表内数据，待完善至listview界面删除，目前只需要输入ID即可
        Button student_delete = findViewById(R.id.button_delete);
        student_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = student_id.getText().toString().trim();
                int res = db.delete("test_student","student_id=?",new String[]{id});
                if(res>0){
                    Log.e("zhu","删除成功，res："+res);
                }else{
                    Log.e("zhu","删除失败，res："+res);
                }
            }
        });
    }

}
