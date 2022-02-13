package com.News.ns2;

import java.io.Serializable;

public class NewsData implements Serializable {

    String title;
    String urlTolmage;
    String content;

    public NewsData(){

    }

    public String getTitle() {
        return title;
    }

    public String getUrlTolmage() {
        return urlTolmage;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrlTolmage(String urlTolmage) {
        this.urlTolmage = urlTolmage;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
