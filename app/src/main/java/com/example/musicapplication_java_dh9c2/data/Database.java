package com.example.musicapplication_java_dh9c2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.Song;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "t3.db";
    private static final String TABLE_NAME = "Song";
    private static final int VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Truy vấn không trả kết quả : create , insert , update ,delete...
    public void QueryData(String sql){
        // ghi vào dữ liệu vào getWritableDatabase();
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    // Truy vấn có trả về : Select
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), singer VARCHAR(200), file INTEGER, image INTEGER)";
        sqLiteDatabase.execSQL(sql);

        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Chỉ vì quá yêu em' , 'Huy Vạc', '"+ R.raw.chi_vi_qua_yeu_em+"', '"+R.drawable.chi_vi_qua_yeu_em+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Anh đã sai', 'Onlyc', '"+R.raw.anh_da_sai_onlyc+"', '"+R.drawable.anh_da_sai_onlyc1+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Đợi bao lâu không đáng sợ', 'Trang Thiên', '"+R.raw.doi_bao_lau_khong_dang_so+"', '"+R.drawable.doi_bao_lau_khong_dang_so1+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Đứng ai nhắc về anh ấy', 'Trà My', '"+R.raw.dung_ai_nhac_ve_anh_ay+"', '"+R.drawable.dung_ai_nhac_ve_anh_ay+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Ông trời làm tội anh chưa', 'Tuấn Cry', '"+R.raw.ong_troi_lam_toi_anh_chua+"', '"+R.drawable.ong_troi_lam_toi_anh_chua+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Người đáng thương là anh', 'Onlyc', '"+R.raw.nguoi_dang_thuong_la_anh+"', '"+R.drawable.nguoi_dang_thuong_la_anh+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Nàng thơ', 'Hoàng Dũng', '"+R.raw.nang_tho+"', '"+R.drawable.nang_tho+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Đau để trưởng thành', 'Onlyc', '"+R.raw.dau_de_truong_thanh+"', '"+R.drawable.dau_de_truong_thanh1+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Câu hứa chưa vẹn tròn', 'Phát Huy', '"+R.raw.cau_hua_chua_ven_tron+"', '"+R.drawable.cau_hua_chua_ven_tron+"')");
        sqLiteDatabase.execSQL("INSERT INTO Song VALUES(null, 'Sắp 30', 'Trịnh Đình Quang', '"+R.raw.sap_30+"', '"+R.drawable.sap_30+"')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // lấy toàn bộ thông tin bài hát
    public List<Song> TTBaiHat() {
        List<Song> listSong = new ArrayList<>();
        String sql2 = "Select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql2, null);
        if (cursor.moveToFirst()) {
            do {
                Song s = new Song();
                s.setId(cursor.getInt(0));
                s.setTitle(cursor.getString(1));
                s.setSinger(cursor.getString(2));
                s.setFile(cursor.getInt(3));
                s.setImage(cursor.getInt(4));

                listSong.add(s);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return listSong;
    }
}
