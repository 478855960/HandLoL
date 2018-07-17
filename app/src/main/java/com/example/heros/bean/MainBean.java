package com.example.heros.bean;

/**
 * Created by DELL on 2018/7/17.
 */

public class MainBean {
    public String img;
    public String title;
    public String content;
    public String time;

    @Override
    public String toString() {
        return "MainBean{" +
                "img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
