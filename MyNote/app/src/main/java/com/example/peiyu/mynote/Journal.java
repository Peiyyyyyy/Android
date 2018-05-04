package com.example.peiyu.mynote;

public class Journal {
    private String title;
    private String time;
    private String content;
    private String username;
    public Journal(String user,String title,String time,String content ){
        this.username = user;
        this.title = title;
        this.time = time;
        this.content = content;
    }
    public String getTitle(){
        return title;
    }
    public String getTime(){
        return time;
    }
    public String getContent(){
        return content;
    }
    public void setTitle(String title){
        this.title = title;

    }
    public void setTime(String time){
        this.time = time;

    }
    public void setContent(String content){
        this.content = content;
    }
}
