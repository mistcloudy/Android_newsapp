package com.News.ns2.Recycler;

public class ItemObject {
    private String title;
    private String release;



    public ItemObject(String title,  String release){
        this.title = title;
        this.release = release;
    }


    public String getTitle() {
        return title;
    }


    public String getRelease() {
        return release;
    }


}