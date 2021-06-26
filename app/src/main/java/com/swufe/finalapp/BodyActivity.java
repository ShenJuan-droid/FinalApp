package com.swufe.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BodyActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText noteContent;
    private Button save;
    private Button delete;
    private Button back;
    private DBHelper dBHelper;
    private String id;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        //初始化控件
        noteContent = (EditText) findViewById(R.id.note_content);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
        back = (Button) findViewById(R.id.back);

        //对点击事件的控件进行监听处理
        save.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);

        //打开数据库、数据表
        dBHelper = new DBHelper(this);

        //判断是添加记录还是修改记录
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if(id != null){
            //修改记录
            noteContent.setText(intent.getStringExtra("curContent"));
        }
        //id不存在则为添加记录
    }

    //保存、删除、返回功能的实现
    public void onClick(View view){
        switch(view.getId()){
            case R.id.delete:
                noteContent.setText("");
                break;
            case R.id.back:
                finish();
                break;
            case R.id.save:
                //获得用户输入内容和系统时间
                String noteContent1 = noteContent.getText().toString().trim();
                time = getTime();
                //判断是新添加保存还是修改后保存
                if(id != null){
                    //修改后保存
                    if(noteContent1.length()>0){
                        int i = dBHelper.update(id,noteContent1);
                        if(i > 0){
                            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                            setResult(2);
                            finish();
                        }
                    }
                }else{
                    //新添加保存
                    if(noteContent1.length()>0){
                        long n = dBHelper.add(noteContent1,time);
                        if(n>0){
                            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                            setResult(2);
                            finish();
                        }
                    }
                }
                break;
        }
    }
    //获取当前系统时间
    private String getTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date);
    }

}
