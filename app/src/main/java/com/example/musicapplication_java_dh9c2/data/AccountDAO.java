package com.example.musicapplication_java_dh9c2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicapplication_java_dh9c2.Account;

public class AccountDAO {
    private Database database;

    public AccountDAO(Context context) {
        database = new Database(context);
    }

    public Account GetAccount(String u, String p) {
        Account acc = new Account();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT * FROM Account WHERE username = '" + u + "' AND passwd = '" + p + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String userName = cursor.getString(0);
            String passWord = cursor.getString(1);
            String email = cursor.getString(2);
            acc = new Account(userName, passWord, email);
            cursor.moveToNext();
        }
        cursor.close();
        return acc;
    }

    public boolean CheckAccount(String u, String p) {
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT * FROM Account WHERE username = '" + u + "' AND passwd = '" + p + "'";
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

    // Thêm account
    public boolean ThemACC(Account acc) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", acc.getUserName());
        values.put("passwd", acc.getPassWord());
        values.put("email", acc.getEmail());
        long r = db.insert("Account", null, values);
        db.close();
        return r > 0;
    }
}
