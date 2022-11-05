package com.example.musicapplication_java_dh9c2.model;

public class FavoriteSong {
    int idLike;
    int idSong;
    String userName;

    public FavoriteSong() {
    }

    public FavoriteSong(int idLike, int idSong, String userName) {
        this.idLike = idLike;
        this.idSong = idSong;
        this.userName = userName;
    }

    public int getIdLike() {
        return idLike;
    }

    public void setIdLike(int idLike) {
        this.idLike = idLike;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
