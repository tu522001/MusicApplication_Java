package com.example.musicapplication_java_dh9c2.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.model.Song;
import com.example.musicapplication_java_dh9c2.songactivity.SongItemActivity;
import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.MusicDAO;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MusicListFragment extends Fragment {
    private RecyclerView recyclerView;
    MusicDAO database;
    List<Song> arraySong = new ArrayList<Song>();
    CustomAdapter adapter;
    private static int viTri = -1;

    public static final String SHARED_PREFS = "user_prefs";
    public static final String USERNAME_KEY = "username_key";
    SharedPreferences sharedpreferences;
    private String usernameID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        database = new MusicDAO(getActivity());
        arraySong = database.TTBaiHat();
        adapter = new CustomAdapter((ArrayList<Song>) arraySong);

        sharedpreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        usernameID = sharedpreferences.getString(USERNAME_KEY, null);

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
        registerForContextMenu(recyclerView);
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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        MenuItem item = menu.getItem(1);
        item.setVisible(false);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        viTri = adapter.getPosition();
        int idSong = arraySong.get(viTri).getId();

        if (item.getItemId() == R.id.mni_favor) {
            boolean a = database.addLoveMusic(idSong, usernameID);
            Snackbar snackbar;
            if (a) {
                snackbar = Snackbar.make(requireView(), "Đã thêm vào bài hát yêu thich!", Snackbar.LENGTH_SHORT);
                adapter.notifyDataSetChanged();
            } else {
                snackbar = Snackbar.make(requireView(), "Bài hát đã ở trong danh sách yêu thích!!", Snackbar.LENGTH_SHORT);
            }
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
        return super.onContextItemSelected(item);
    }
}