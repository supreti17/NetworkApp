package com.surajapps.networkapp;

public class Book {

    private String title, description, coverImg;

    public Book(String title, String description, String coverImg) {
        this.title = title;
        this.description = description;
        this.coverImg = coverImg;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImg() {
        return coverImg;
    }

}
