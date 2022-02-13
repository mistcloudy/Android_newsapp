package com.News.ns2;

public class RecyclerItem {

    String title ;
    String inputtext;


    public RecyclerItem(String title, String inputtext){


        this.title=title;
        this.inputtext=inputtext;
    }

    public void setText(String title) {
        this.title = title ;
    }

    public String getText() {
        return this.title ;
    }

    public String getInputtext() {
        return inputtext;
    }

    public void setInputtext(String inputtext) {
        this.inputtext = inputtext;
    }


}


