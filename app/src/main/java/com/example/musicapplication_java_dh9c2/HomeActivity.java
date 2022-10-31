package com.example.musicapplication_java_dh9c2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.Database;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Database database;
    ArrayList<Song> arraySong;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        arraySong = new ArrayList<>();
        adapter = new CustomAdapter(arraySong);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // tạo database Ghi chú

        database = new Database(this, "a3.sqlite", null, 1);
        // tạo bảng Công viêc
        database.QueryData("CREATE TABLE IF NOT EXISTS Song(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), singer VARCHAR(200), file INTEGER, image INTEGER)");

        Log.d("III", String.valueOf(database));
        // Thêm dữ liệu và database
        // insert data
//        database.QueryData("INSERT INTO Song VALUES(null, 'Chỉ vì quá yêu em' , 'Huy Vạc', '"+R.raw.chi_vi_qua_yeu_em+"', '"+R.drawable.chi_vi_qua_yeu_em+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Anh đã sai', 'Onlyc', '"+R.raw.anh_da_sai_onlyc+"', '"+R.drawable.anh_da_sai_onlyc1+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Đợi bao lâu không đáng sợ', 'Trang Thiên', '"+R.raw.doi_bao_lau_khong_dang_so+"', '"+R.drawable.doi_bao_lau_khong_dang_so1+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Đứng ai nhắc về anh ấy', 'Trà My', '"+R.raw.dung_ai_nhac_ve_anh_ay+"', '"+R.drawable.dung_ai_nhac_ve_anh_ay+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Ông trời làm tội anh chưa', 'Tuấn Cry', '"+R.raw.ong_troi_lam_toi_anh_chua+"', '"+R.drawable.ong_troi_lam_toi_anh_chua+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Người đáng thương là anh', 'Onlyc', '"+R.raw.nguoi_dang_thuong_la_anh+"', '"+R.drawable.nguoi_dang_thuong_la_anh+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Nàng thơ', 'Hoàng Dũng', '"+R.raw.nang_tho+"', '"+R.drawable.nang_tho+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Đau để trưởng thành', 'Onlyc', '"+R.raw.dau_de_truong_thanh+"', '"+R.drawable.dau_de_truong_thanh1+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Câu hứa chưa vẹn tròn', 'Phát Huy', '"+R.raw.cau_hua_chua_ven_tron+"', '"+R.drawable.cau_hua_chua_ven_tron+"')");
//        database.QueryData("INSERT INTO Song VALUES(null, 'Sắp 30', 'Trịnh Đình Quang', '"+R.raw.sap_30+"', '"+R.drawable.sap_30+"')");

        readSongData();

        onClick();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void readSongData() {
        // select data
        Cursor dataSong = database.GetData("SELECT * FROM Song");
        // moveToNext là di chuyển kế bên xem thằng kế bên có còn cơ sở dữ liệu hay không nếu nó còn sẽ là true còn nếu nó ngưng sẽ là false
        arraySong.clear();
        while (dataSong.moveToNext()) {
            int id = dataSong.getInt(0);
            String ten = dataSong.getString(1);
            String singerName  = dataSong.getString(2);
            int file  = dataSong.getInt(3);
            int image  = dataSong.getInt(4);

            arraySong.add(new Song(id,ten,singerName,file,image));
            Log.d("XXX", String.valueOf(dataSong));
        }
        adapter.notifyDataSetChanged();
    }

    public void onClick(){

        // Cái này đùng để click vào những Item trong Recyclerview
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("AAA", String.valueOf(position));
                // Tạo Intent để chuyển đổi giữa 2 màn hình trong android
                Intent intent = new Intent(HomeActivity.this, SongItemActivity.class);
                intent.putExtra("ArraySong",arraySong);

                Log.d("LLL", "ArrayList in HomeActivity : "+String.valueOf(arraySong));
                intent.putExtra("database", String.valueOf(database));
                intent.putExtra("position",position);

                startActivity(intent);

            }
        });
    }
}