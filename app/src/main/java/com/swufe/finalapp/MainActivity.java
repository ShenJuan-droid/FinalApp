package com.swufe.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "intent" ;
    private DBHelper dBHelper;
    private List<RecordBean> recordBeanList;
    private Adapter adapter;
    private ListView listview;
    private Button add;
    private static final int SEND_CORD = 1111;  //请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();
        
        //显示数据库中的记录到列表控件
        dBHelper= new DBHelper(this);
        //查询且展示数据
        showData();
                
        //添加记录按钮，给按钮添加监听，跳转到BodyActivity
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
        //如果集合中有数据先清空，再把新获取的展示出来（用于修改或删除）
        if(recordBeanList != null){
            recordBeanList.clear();
        }
        //显示数据库中的记录到列表控件
        recordBeanList = dBHelper.listAll();  //查询数据
        adapter = new Adapter(this,R.layout.list_item,recordBeanList);  //将集合传给适配器
        listview.setAdapter(adapter);  //将适配器添加到布局

        //点击列表项目，进入BodyActivity界面的编辑状态
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                RecordBean recordBean = recordBeanList.get(i);  //获取集合中记录对象，i为被点击的记录的编号
                Intent intent = new Intent(MainActivity.this,BodyActivity.class);
                intent.putExtra("id",recordBean.getId());
                intent.putExtra("curContent",recordBean.getCurContent());
                startActivityForResult(intent,SEND_CORD);
            }
        });

        //长按删除列表数据项
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i(TAG, "onItemLongClick: 长按列表项position=" + position);
                //删除操作
                //构造对话框进行确认操作
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onClick: 对话框事件处理");
                        recordBeanList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("否",null);

                builder.create().show();
                Log.i(TAG, "onItemLongClick: size=" + recordBeanList.size());

                return true;
            }
        });

    }

    //从BodyActivity界面返回后，刷新列表，显示数据
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //请求码和返回码
        if(requestCode == SEND_CORD && resultCode == RESULT_OK){
            //查询保存在数据库中的全部数据
            showData();
        }
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        add = (Button) findViewById(R.id.add_list);
    }
}
