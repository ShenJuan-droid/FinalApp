package com.swufe.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dBHelper;
    private List<RecordBean> recordBeanList;
    private Adapter adapter;
    private ListView listItem;
    private Button add;
    public static final int SEND_CORD = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();
        
        //显示数据库中的记录到列表控件
        dBHelper= new DBHelper(this);
        showData();
                
        //添加记录按钮，跳转到BodyActivity
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BodyActivity.class);
                startActivityForResult(intent,SEND_CORD);
            }
        });
    }

    //显示数据库中的记录到列表控件
    private void showData() {
        if(recordBeanList != null){
            recordBeanList.clear();
        }
        recordBeanList = dBHelper.listAll();  //查询数据
        adapter = new Adapter(this,recordBeanList);
        listItem.setAdapter(adapter);

        //点击列表项目，进入BodyActivity界面的编辑状态
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                RecordBean recordBean = recordBeanList.get(i);
                Intent intent = new Intent(MainActivity.this,BodyActivity.class);
                intent.putExtra("id",recordBean.getId());
                intent.putExtra("curContent",recordBean.getCurContent());
                startActivityForResult(intent,SEND_CORD);
            }
        });

        //长按删除列表数据项

    }

    /*//从BodyActivity界面返回后，刷新列表，显示数据
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SEND_CORD && resultCode == RESULT_OK){
            //查询保存在数据库中的全部数据
            showData();
        }
    }*/

    private void initView() {
        listItem = (ListView) findViewById(R.id.list_item);
        add = (Button) findViewById(R.id.add_list);
    }
}
