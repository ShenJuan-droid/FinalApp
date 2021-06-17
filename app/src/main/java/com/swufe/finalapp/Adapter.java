package com.swufe.finalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context context;
    private List<RecordBean> recordBeanList;

    public Adapter(@NonNull Context context, @NonNull List<RecordBean> recordBeanList) {
        this.context = context;
        this.recordBeanList = recordBeanList;
    }

    public int getCount(){
        return recordBeanList == null ? 0 : recordBeanList.size();
    }

    public Object getItem(int i){
        return recordBeanList.get(i);
    }

    public long getItemId(int i){
        return i;
    }

    public View getView(int i, View itemView, ViewGroup parent) {
        //可复用记录的方法
        ViewHolder viewHolder;
        if(itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            viewHolder = new ViewHolder(itemView);
            itemView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) itemView.getTag();
        }
        //取集合中记录内容
        RecordBean recordBean = recordBeanList.get(i);

        //给记录控件赋值
        viewHolder.item.setText(recordBean.getCurContent());
        return itemView;
    }

    class ViewHolder{
        TextView item;

        public ViewHolder(View itemView){
            item = itemView.findViewById(R.id.itemTitle);
        }
    }

}