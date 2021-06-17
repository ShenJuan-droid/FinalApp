package com.swufe.finalapp;

//存放记录对象，即每一条记录
public class RecordBean {

    private String id;             //每条记事的id,即编号
    private String curContent;  //记事内容

    public RecordBean(){
    }

    public RecordBean(String id,String curContent){
        this.id = id;
        this.curContent = curContent;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurContent(){
        return curContent;
    }

    public void setCurContent(String curContent) {
        this.curContent = curContent;
    }
}
