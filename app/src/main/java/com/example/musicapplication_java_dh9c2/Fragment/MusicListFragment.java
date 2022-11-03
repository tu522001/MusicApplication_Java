package com.example.musicapplication_java_dh9c2.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicapplication_java_dh9c2.HomeActivity;
import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.Song;
import com.example.musicapplication_java_dh9c2.SongItemActivity;
import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicListFragment extends Fragment {
    private RecyclerView recyclerView;
    Database database;
    List<Song> arraySong = new ArrayList<Song>();
    CustomAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        database = new Database(getActivity());
        arraySong = database.TTBaiHat();
        adapter = new CustomAdapter((ArrayList<Song>) arraySong);


        onClick();
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.MusicListView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);
        initView(view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        database.TTBaiHat();

        return view;
    }

    private void onClick() {
        // Cái này đùng để click vào những Item trong Recyclerview
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("AAA", String.valueOf(position));
                // Tạo Intent để chuyển đổi giữa 2 màn hình trong android
                Intent intent = new Intent(getActivity(), SongItemActivity.class);
                intent.putExtra("ArraySong", (ArrayList<Song>) arraySong);

                Log.d("LLL", "ArrayList in HomeActivity : " + String.valueOf(arraySong));
                intent.putExtra("position", position);

                startActivity(intent);

            }
        });
    }

}