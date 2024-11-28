package com.example.lap7;

import java.util.HashMap;

public class ToDo {
    private String id,title,content,date,type;

    public ToDo(String id, String title, String content, String date, String type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.type = type;
    }
    //viet phuong thuc xu ly du lieu voi firebase
    public HashMap<String,Object> conveHashMap(){
        HashMap<String,Object> work = new HashMap<>();
        work.put("id",id);
        work.put("title",title);
        work.put("content",content);
        work.put("date",date);
        work.put("type",type);
        return work;
    }

    public ToDo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
