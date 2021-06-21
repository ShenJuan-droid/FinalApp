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
    public static final String CONTENT = "curContent";  //记录内容字段

    //创建数据表的sql语句
    public static final String CREATE_TABLE = "CREATE TABLE "+ TB_NAME
            +"(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CONTENT + " TEXT)";


    //创建数据表对象
    private SQLiteDatabase db;

    //构造方法
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);   //创建数据库
        db = this.getWritableDatabase();   //初始化，创建可读写数据表
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //向数据库添加记录
    public long add(String curContent){
        ContentValues values = new ContentValues();
        values.put(CONTENT,curContent);
        return db.insert(TB_NAME, null, values); //返回值>0，表示添加成功
    }


    //删除记录
    public int delete(String id){
        return db.delete(TB_NAME, "id=?", new String[]{id}); //返回值的作用：判断删除的记录条数是否为0，为0则删除失败，不为0则删除成功
    }

    //修改记录
    public int update(String id, String curContent) {
        ContentValues values = new ContentValues();
        values.put(CONTENT,curContent);
        return db.update(TB_NAME, values, "id=?'", new String[]{id});  //根据id修改；返回值的作用：判断修改的记录条数是否为0
    }

    //查询全部记录
    public List<RecordBean> listAll() {
        Cursor cursor = db.query(TB_NAME, null, null, null, null, null, null);
        List<RecordBean> recordBeanList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
               String id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
               String curContent  = cursor.getString(cursor.getColumnIndexOrThrow(CONTENT));
               //生成记录对象
               RecordBean bean = new RecordBean(id,curContent);
               //将记录对象添加到集合中
               recordBeanList.add(bean);
            }
            cursor.close();
        }
        return recordBeanList;
    }
}
