package com.example.musicapplication_java_dh9c2.songactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.MusicDAO;
import com.example.musicapplication_java_dh9c2.model.Song;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView recyclerViewF;
    MusicDAO mDAO;
    List<Song> FavorSong = new ArrayList<Song>();
    CustomAdapter adapter;
    String usernameId;
    private static int mCurrentItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        initView();

        Bundle getEx = getIntent().getExtras();
        usernameId = getEx.getString("username");

        mDAO = new MusicDAO(this);
        FavorSong = mDAO.GetAllLoveMusic(usernameId);
        adapter = new CustomAdapter((ArrayList<Song>) FavorSong);
        CustomAdapter.setUsernameID(usernameId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewF.setLayoutManager(linearLayoutManager);
        recyclerViewF.setAdapter(adapter);
        registerForContextMenu(recyclerViewF);
        onClick();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
        MenuItem i = menu.getItem(0);
        i.setVisible(false);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        mCurrentItemPosition = adapter.getPosition();
        int idSong = FavorSong.get(mCurrentItemPosition).getId();

        if (item.getItemId() == R.id.mni_favor_del) {
            int isDeleteLikeSucess = mDAO.DeleteLoveMusic(idSong, usernameId);
            if (isDeleteLikeSucess == 1) {
                Toast.makeText(this, "Đã xoá khỏi danh sách yêu thích! ", Toast.LENGTH_SHORT).show();
                FavorSong.remove(mCurrentItemPosition);
                adapter.notifyDataSetChanged();
            }
        }
        return super.onContextItemSelected(item);
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

    }
}