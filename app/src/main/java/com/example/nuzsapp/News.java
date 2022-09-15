package com.example.nuzsapp;

public class News {
    String author;
    String title;
    String url;
    String imageUrl;


    News(String author,String title,String url, String imageUrl){
        this.author=author;
        this.imageUrl=imageUrl;
        this.title=title;
        this.url=url;
    }
}
