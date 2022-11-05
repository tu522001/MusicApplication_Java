package com.example.musicapplication_java_dh9c2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicapplication_java_dh9c2.model.FavoriteSong;
import com.example.musicapplication_java_dh9c2.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicDAO {
    private final Database database;

    public MusicDAO(Context context) {
        database = new Database(context);

    }

    // lấy toàn bộ thông tin bài hát
    public List<Song> TTBaiHat() {
        List<Song> listSong = new ArrayList<>();
        String sql = "Select * from Song";
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Song s = new Song();
                s.setId(cursor.getInt(0));
                s.setTitle(cursor.getString(1));
                s.setSinger(cursor.getString(2));
                s.setFile(cursor.getInt(3));
                s.setImage(cursor.getInt(4));

                listSong.add(s);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return listSong;
    }


    public List<Song> GetAllLoveMusic(String username) {
        List<Song> listSong = new ArrayList<>();
        String sql = "Select Song.ID, Song.title, Song.singer, Song.file, Song.image from Song " +
                "INNER JOIN Favourites ON  Song.ID = Favourites.idSong " +
                "INNER JOIN Account ON Account.username = Favourites.usernameID " +
                "Where Account.username = '" + username + "'";
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Song s = new Song();
                s.setId(cursor.getInt(0));
                s.setTitle(cursor.getString(1));
                s.setSinger(cursor.getString(2));
                s.setFile(cursor.getInt(3));
                s.setImage(cursor.getInt(4));

                listSong.add(s);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return listSong;
    }

    public boolean CheckLoveMusic(int songID, String usernameID) {
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT * FROM Favourites WHERE idSong = " + songID + " AND usernameID = '" + usernameID + "' ";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean addLoveMusic(int idSong, String usernameID) {
        if (!CheckLoveMusic(idSong, usernameID)) {
            SQLiteDatabase db = database.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("usernameID", usernameID);
            values.put("idSong", idSong);
            long r = db.insert("Favourites", null, values);
            db.close();
            return r > 0;
        }

        return false;
    }

    // Xóa bài hát trong list yêu thích;
    public int DeleteLoveMusic(int idSong, String usernameID) {
        int kq = 0;
        if (CheckLoveMusic(idSong, usernameID)) {
            FavoriteSong f = GetLoveMusic(idSong, usernameID);
            SQLiteDatabase db = database.getWritableDatabase();
            kq = db.delete("Favourites", "IdLike=?", new String[]{String.valueOf(f.getIdLike())});
            db.close();
        }
        return kq;
    }

    public FavoriteSong GetLoveMusic(int idSong, String usernameID) {
        FavoriteSong s = new FavoriteSong();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT * FROM Favourites WHERE idSong = '" + idSong + "' AND usernameID = '" + usernameID + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int songID = cursor.getInt(2);
            String user = cursor.getString(1);
            s = new FavoriteSong(id, songID, user);
            cursor.moveToNext();
        }
        cursor.close();
        return s;
    }
}
