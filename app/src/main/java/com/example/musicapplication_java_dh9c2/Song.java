package com.example.musicapplication_java_dh9c2;

import java.io.Serializable;

public class Song implements Serializable {
    int Id;
    String title;
    String singer;
    int file;
    int image;

    public Song(int id, String title, String singer, int file, int image) {
        Id = id;
        this.title = title;
        this.singer = singer;
        this.file = file;
        this.image = image;
    }

    public Song() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
