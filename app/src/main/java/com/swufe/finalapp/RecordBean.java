package com.swufe.finalapp;

//存放记录对象，即每一条记录
public class RecordBean {

    private String id;          //每条记录的id,即编号
    private String curContent;  //记录内容
    private String time;        //创建时间

    public RecordBean() {
    }

    public RecordBean(String id, String curContent, String time) {
        this.id = id;
        this.curContent = curContent;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurContent() {
        return curContent;
    }

    public void setCurContent(String curContent) {
        this.curContent = curContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
