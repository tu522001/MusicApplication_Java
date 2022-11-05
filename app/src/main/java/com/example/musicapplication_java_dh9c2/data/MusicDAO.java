package com.example.musicapplication_java_dh9c2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicapplication_java_dh9c2.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicDAO {
    private Database database;

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
}
