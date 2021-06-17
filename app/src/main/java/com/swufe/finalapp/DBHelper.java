package com.swufe.finalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    //数据库
    private static final String DB_NAME = "NoteDB1.db";     //数据库名
    public static final String TB_NAME = "tb_note1";        //表名
    public static final String ID = "id";                  //id字段
    public static final String CURCONTEXT = "curContent";  //记录内容字段


    //创建数据表对象
    private SQLiteDatabase db;

    //构造方法
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);   //创建数据库
        db = this.getWritableDatabase();   //实例化，创建可读写数据表
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        db.execSQL("CREATE TABLE "+TB_NAME+"(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CURCONTEXT +" TEXT)");
    }

    @Override
    //?
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tb_note");
        onCreate(db);

    }

    //向数据库添加记录
    public long add(String curContent){
        ContentValues values = new ContentValues();
        values.put(CURCONTEXT, curContent);
        return db.insert(TB_NAME, null, values);
    }

    //删除记录
    public int delete(String id){
        return db.delete(TB_NAME, "id=?", new String[]{id});
    }

    //修改记录
    public int update(String id, String curContent) {
        ContentValues values = new ContentValues();
        values.put(CURCONTEXT, curContent);
        return db.update(TB_NAME, values, "id=?'", new String[]{id});
    }

    //查询全部记录
    public List<RecordBean> listAll() {
        Cursor cursor = db.query(TB_NAME, null, null, null, null, null, null);
        List<RecordBean> recordBeanList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
               /* RecordBean bean = new RecordBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                bean.setCurContent(cursor.getString(cursor.getColumnIndex(CURCONTEXT)));
                recordBeanList.add(bean);*/
               String id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
               String curContent  = cursor.getString(cursor.getColumnIndexOrThrow("CURCONTEXT"));
               RecordBean bean = new RecordBean(id,curContent);
               recordBeanList.add(bean);
            }
            cursor.close();
        }
        return recordBeanList;

    }
}
