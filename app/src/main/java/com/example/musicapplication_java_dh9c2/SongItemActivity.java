package com.example.musicapplication_java_dh9c2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.Database;

import java.util.ArrayList;

public class SongItemActivity extends AppCompatActivity {

    private CardView cardView;
    private SeekBar seekBar;
    private ImageButton imageButtonPrev, ButtonPlay, imageButtonStop, imageButtonNext;
    private Database databases;
    ArrayList<Song> arraySongs;
    CustomAdapter adapter;
    MediaPlayer mediaPlayer;
//    int position = 0;

    HomeActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_item);
        init();


        arraySongs = new ArrayList<Song>();
        arraySongs = (ArrayList<Song>) getIntent().getSerializableExtra("ArraySong");
        adapter = new CustomAdapter(arraySongs);

//        databases = new Database(this, "a3.sqlite", null, 1);
//        databases = (Database) getIntent().getSerializableExtra("database") ;

        databases = new Database(this, "a3.sqlite", null, 1);
        // tạo bảng Công viêc
        databases.QueryData("CREATE TABLE IF NOT EXISTS Song(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), singer VARCHAR(200), file INTEGER, image INTEGER)");

        Cursor dataSong = databases.GetData("SELECT * FROM Song");
        Log.d("LLL", String.valueOf(arraySongs));
        Log.d("MMM", "databases : " + String.valueOf(databases));

        Log.d("XXX", "Activity 2 : " + String.valueOf(dataSong));


//        activity.onClick();


//        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
////               mediaPlayer = MediaPlayer.create(SongItemActivity.this, arraySongs.get(1).getFile());
//                Log.d("AAA","Activity 2 position : "+ String.valueOf(position));
//                ButtonPlay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(view.getContext(), " Button Play ", Toast.LENGTH_SHORT).show();
//                        if (mediaPlayer.isPlaying()) {
//                            // Nếu đang phát -> pause -> đổi hình play
//                            mediaPlayer.pause();
//                            ButtonPlay.setImageResource(R.drawable.ic_play);
//                        } else {
//                            // đang ngừng -> phát  -> đổi hình pause
//                            mediaPlayer.start();
//                            ButtonPlay.setImageResource(R.drawable.ic_pause);
//                        }
//                        Log.d("AAA","Activity 2 position : "+ String.valueOf(position));
//                    }
//                });
//            }
//        });

    }

    private void init() {
        cardView = (CardView) findViewById(R.id.cardView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        imageButtonPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
        ButtonPlay = (ImageButton) findViewById(R.id.ButtonPlay);
        imageButtonStop = (ImageButton) findViewById(R.id.imageButtonStop);
        imageButtonNext = (ImageButton) findViewById(R.id.imageButtonNext);
    }


//    private void readSongData() {
//        // select data
//        Cursor dataSong = databases.GetData("SELECT * FROM Song");
//        // moveToNext là di chuyển kế bên xem thằng kế bên có còn cơ sở dữ liệu hay không nếu nó còn sẽ là true còn nếu nó ngưng sẽ là false
//        arraySongs.clear();
//        while (dataSong.moveToNext()) {
//            int id = dataSong.getInt(0);
//            String ten = dataSong.getString(1);
//            String singerName  = dataSong.getString(2);
//            int file  = dataSong.getInt(3);
//            int image  = dataSong.getInt(4);
//
//            arraySongs.add(new Song(id,ten,singerName,file,image));
//        }
//        adapter.notifyDataSetChanged();
//    }

}