package com.example.musicapplication_java_dh9c2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication_java_dh9c2.Fragment.MusicListFragment;
import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.Database;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Database database;
    List<Song> arraySong = new ArrayList<Song>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        database = new Database(this);

        arraySong = database.TTBaiHat();
        adapter = new CustomAdapter((ArrayList<Song>) arraySong);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        database.TTBaiHat();

        onClick();







    }



    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
    }

//

    public void onClick() {

        // Cái này đùng để click vào những Item trong Recyclerview
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("AAA", String.valueOf(position));
                // Tạo Intent để chuyển đổi giữa 2 màn hình trong android
                Intent intent = new Intent(HomeActivity.this, SongItemActivity.class);
                intent.putExtra("ArraySong", (ArrayList<Song>) arraySong);

                Log.d("LLL", "ArrayList in HomeActivity : " + String.valueOf(arraySong));
                intent.putExtra("position", position);

                startActivity(intent);

            }
        });


//        private void readSongData() {
//        // select data
//        Cursor dataSong = database.GetData("SELECT * FROM Song");
//        // moveToNext là di chuyển kế bên xem thằng kế bên có còn cơ sở dữ liệu hay không nếu nó còn sẽ là true còn nếu nó ngưng sẽ là false
//        arraySong.clear();
//        while (dataSong.moveToNext()) {
//            int id = dataSong.getInt(0);
//            String ten = dataSong.getString(1);
//            String singerName = dataSong.getString(2);
//            int file = dataSong.getInt(3);
//            int image = dataSong.getInt(4);
//
//            arraySong.add(new Song(id, ten, singerName, file, image));
//            Log.d("XXX", String.valueOf(dataSong));
//        }
//        adapter.notifyDataSetChanged();
//    }
    }
}