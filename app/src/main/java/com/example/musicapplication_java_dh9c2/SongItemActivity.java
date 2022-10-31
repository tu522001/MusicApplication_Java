package com.example.musicapplication_java_dh9c2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.Database;

import java.util.ArrayList;

public class SongItemActivity extends AppCompatActivity {

    private CardView cardView;
    private SeekBar seekBar;
    private ImageButton btnPrev, btnPlay, btnStop, btnNext;
    private ImageView imageView2;
    private Database databases;
    private ArrayList<Song> arraySongs;
    private CustomAdapter adapter;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer1;
    private TextView textViewName,textViewID;
    private Song songs;
    private Animation animation;
    private int position;
    private int INDEX=12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_item);
        init();

        // hình quay
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        // arrayList
        arraySongs = new ArrayList<Song>();
        arraySongs = (ArrayList<Song>) getIntent().getSerializableExtra("ArraySong");
        adapter = new CustomAdapter(arraySongs);

        databases = new Database(this, "a3.sqlite", null, 1);
        // tạo bảng Công viêc
        databases.QueryData("CREATE TABLE IF NOT EXISTS Song(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(200), singer VARCHAR(200), file INTEGER, image INTEGER)");

        Cursor dataSong = databases.GetData("SELECT * FROM Song");

        Log.d("LLL", String.valueOf(arraySongs));
        Log.d("MMM", "databases : " + String.valueOf(databases));

        Log.d("XXX", "Activity 2 : " + String.valueOf(dataSong));

        Intent intent = getIntent();
        position = intent.getIntExtra("position", INDEX);

        khoiTao();

//
//        arraySongs.get(position).getTitle();
        songs = arraySongs.get(position);

        hideView();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mediaPlayer.isPlaying()) {
                    imageView2.clearAnimation();
                    // Nếu đang phát -> pause -> đổi hình play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play);

                } else {
                    readSongData();
                    imageView2.startAnimation(animation);
                    // đang ngừng -> phát  -> đổi hình pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                readSongData();
                Log.d("PPPSS","getTitle : "+ arraySongs.get(position).getTitle());
                Log.d("PPPSS","getFile : "+ arraySongs.get(position).getFile());
                Log.d("PPPSS","getImage : "+ arraySongs.get(position).getImage());
                Log.d("PPPSS","position : "+ position);
                if ((position > arraySongs.size() -1)) {
                    adapter.notifyDataSetChanged();
                    position = 0;
                    imageView2.clearAnimation();
                }
                // xử lý sự kiện để không bị hát trồng lên nhau
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    imageView2.clearAnimation();
                }
                khoiTao();
//                arraySongs.get(position).getTitle();
//                arraySongs.get(position).getFile();
//                arraySongs.get(position).getImage();
//                textViewName.setText(songs.getTitle());
//                imageView2.setImageResource(songs.getImage());
//                Log.d("PPPSS","Title : "+ arraySongs.get(position).getTitle());
                readSongData();


                textViewName.setText(songs.getTitle());
                btnPlay.setImageResource(R.drawable.ic_pause);
                imageView2.startAnimation(animation);
                mediaPlayer.start();

                adapter.notifyDataSetChanged();
            }
        });


        btnStop.setOnClickListener((view) -> {
            mediaPlayer.stop();
            mediaPlayer.release(); // dừng bài hát
            btnPlay.setImageResource(R.drawable.ic_play);
            khoiTao();
        });
    }

    private void khoiTao() {
        mediaPlayer = MediaPlayer.create(SongItemActivity.this, arraySongs.get(position).getFile());
        textViewName.setText(arraySongs.get(position).getTitle());
        Log.d("IIO","arraySongs.get(position).getFile() : "+arraySongs.get(position).getFile()+
                " , arraySongs.get(position).getTitle() "+arraySongs.get(position).getTitle()+
                " , position"+position);
    }

    private void hideView() {
        textViewName.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        textViewID.setVisibility(View.GONE);
    }

    private void init() {
        cardView = (CardView) findViewById(R.id.cardView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btnPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
        btnPlay = (ImageButton) findViewById(R.id.ButtonPlay);
        btnStop = (ImageButton) findViewById(R.id.imageButtonStop);
        btnNext = (ImageButton) findViewById(R.id.imageButtonNext);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewID = (TextView) findViewById(R.id.textViewID);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
    }


    private void readSongData() {
        // select data
        Cursor dataSong = databases.GetData("SELECT * FROM Song");
        // moveToNext là di chuyển kế bên xem thằng kế bên có còn cơ sở dữ liệu hay không nếu nó còn sẽ là true còn nếu nó ngưng sẽ là false
        arraySongs.clear();
        while (dataSong.moveToNext()) {
            int id = dataSong.getInt(0);
            String ten = dataSong.getString(1);
            String singerName = dataSong.getString(2);
            int file = dataSong.getInt(3);
            int image = dataSong.getInt(4);
            arraySongs.add(new Song(id, ten, singerName, file, image));


            textViewName.setText(songs.getTitle());
            imageView2.setImageResource(songs.getImage());
            Log.d("GGG", "ID: " + String.valueOf(id) + " , Tên : " + String.valueOf(ten));
            textViewName.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);

        }
        adapter.notifyDataSetChanged();
    }

}