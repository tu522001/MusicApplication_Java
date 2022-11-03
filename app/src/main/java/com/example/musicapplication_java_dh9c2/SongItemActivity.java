package com.example.musicapplication_java_dh9c2;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.Database;

import java.util.ArrayList;
import java.util.List;

public class SongItemActivity extends AppCompatActivity {

    private CardView cardView;
    private SeekBar seekBar;
    private ImageButton btnPrev, btnPlay, btnStop, btnNext;
    private ImageView imageView2;
    private Database databases;
    private List<Song> arraySongs = new ArrayList<Song>();
    private CustomAdapter adapter;
    private MediaPlayer mediaPlayer;
    private TextView textViewName, textViewID;
    private Song songs;
    private Animation animation;
    private int position;
    //   private int INDEX = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_item);

        init();

        databases = new Database(this);

        Bundle intent = getIntent().getExtras();
        position = intent.getInt("position");

        // hình quay
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        // arrayList
        arraySongs = (ArrayList<Song>) getIntent().getSerializableExtra("ArraySong");
        adapter = new CustomAdapter((ArrayList<Song>) arraySongs);

        // lấy dữ liệu bài hát
        songs = arraySongs.get(position);
        textViewName.setText(songs.getTitle());
        imageView2.setImageResource(songs.getImage());
        textViewName.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.VISIBLE);

        Log.d("LLL", String.valueOf(arraySongs));
        Log.d("MMM", "databases : " + String.valueOf(databases));


        khoiTao();
        //   hideView();


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    imageView2.clearAnimation();
                    // Nếu đang phát -> pause -> đổi hình play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play);

                } else {
                    databases.TTBaiHat();
                    imageView2.startAnimation(animation);
                    // đang ngừng -> phát  -> đổi hình pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        // tự động click vào nút play (tự play)
        btnPlay.performClick();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position >= arraySongs.size() - 1) {
                    position = 0;
                    songs = arraySongs.get(position);
                } else {
                    position++;
                    songs = arraySongs.get(position);
                }
                // xử lý sự kiện để không bị hát trồng lên nhau
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    imageView2.clearAnimation();
                }
                databases.TTBaiHat();
                khoiTao();
                adapter.notifyDataSetChanged();
                textViewName.setText(songs.getTitle());
                imageView2.setImageResource(songs.getImage());
                btnPlay.setImageResource(R.drawable.ic_pause);
                imageView2.startAnimation(animation);
                mediaPlayer.start();


            }
        });


        btnStop.setOnClickListener((view) -> {
            mediaPlayer.stop();
            mediaPlayer.release(); // dừng bài hát
            btnPlay.setImageResource(R.drawable.ic_play);
            khoiTao();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    private void khoiTao() {
        mediaPlayer = MediaPlayer.create(SongItemActivity.this, arraySongs.get(position).getFile());
        textViewName.setText(arraySongs.get(position).getTitle());
        Log.d("IIO", "arraySongs.get(position).getFile() : " + arraySongs.get(position).getFile() +
                " , arraySongs.get(position).getTitle() " + arraySongs.get(position).getTitle() +
                " , position" + position);
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