package com.example.musicapplication_java_dh9c2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.MusicDAO;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView recyclerViewF;
    MusicDAO mDAO;
    List<Song> FavorSong = new ArrayList<Song>();
    CustomAdapter adapter;
    private int mCurrentItemPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        initView();
        registerForContextMenu(recyclerViewF);
        Bundle getEx = getIntent().getExtras();
        String username = getEx.getString("username");

        mDAO = new MusicDAO(this);
        FavorSong = mDAO.GetAllLoveMusic(username);
        adapter = new CustomAdapter((ArrayList<Song>) FavorSong);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewF.setLayoutManager(linearLayoutManager);
        recyclerViewF.setAdapter(adapter);

        onClick();
    }

    private void initView() {
        recyclerViewF = findViewById(R.id.MusicFavoritesListView);

    }

    int viTri;
    private void onClick() {
        // Cái này đùng để click vào những Item trong Recyclerview
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                viTri = position;
                Log.d("AAA", String.valueOf(position));
                // Tạo Intent để chuyển đổi giữa 2 màn hình trong android
                Intent intent0 = new Intent(FavouriteActivity.this, SongItemActivity.class);
                intent0.putExtra("ArraySong", (ArrayList<Song>) FavorSong);
                intent0.putExtra("position", position);

                Log.d("ZZZ", "ArrayList in FavoriteAcivity : " + String.valueOf(FavorSong));

                startActivity(intent0);

            }
        });

        adapter.setOnLongItemClickListener(new CustomAdapter.onLongItemClickListener() {
            @Override
            public void ItemLongClicked(View v, int position) {
                mCurrentItemPosition = position;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.h) {
//
//        }
//
//        if (id == R.id.share) {
//
//        }
//
//        return true;
//    }
}