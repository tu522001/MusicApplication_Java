package com.example.musicapplication_java_dh9c2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicapplication_java_dh9c2.R;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "z1.db";
    private static final String TABLE_SONG = "Song";
    private static final String TABLE_ACCOUNT = "Account";
    private static final String TABLE_FAVOR = "Favourites";
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
        String sql1 = "CREATE TABLE IF NOT EXISTS " + TABLE_SONG +"(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), singer VARCHAR(200), file INTEGER, image INTEGER)";
        String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT +"(username VARCHAR(200) PRIMARY KEY, passwd VARCHAR(100), email VARCHAR(150))";
        String sql3 = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVOR +"(IdLike INTEGER PRIMARY KEY AUTOINCREMENT, usernameID VARCHAR(200), idSong INTEGER, FOREIGN KEY(usernameID) REFERENCES Account(username), FOREIGN KEY(idSong) REFERENCES Song(Id))";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);

        sqLiteDatabase.execSQL("INSERT INTO Account VALUES('admin', '12345', 'admin@mail.com')");
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
        sqLiteDatabase.execSQL("INSERT INTO Favourites VALUES(null, 'admin', 5)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
