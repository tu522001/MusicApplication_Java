package com.example.musicapplication_java_dh9c2.songactivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.adapter.CustomAdapter;
import com.example.musicapplication_java_dh9c2.data.MusicDAO;
import com.example.musicapplication_java_dh9c2.model.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SongItemActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private ImageButton btnPrev, btnPlay, btnStop, btnNext;
    private ImageView imageView2;
    private MusicDAO databases;
    private List<Song> arraySongs = new ArrayList<Song>();
    private CustomAdapter adapter;
    private MediaPlayer mediaPlayer;
    private TextView textViewName, textViewTimeStart, textViewTimeEnd;
    private Song songs;
    private Animation animation;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ẩn thanh trạng thái   Hide the Status Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_song_item);

        init();

        databases = new MusicDAO(this);

        // lấy dữ liệu từ position trong RecyclerView vào đây
        Bundle intent = getIntent().getExtras();
        position = intent.getInt("position");

        // khới tạo quay hình ảnh
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        // lấy dữ liệu từ ArrayList của HomeActivity sang
        arraySongs = (ArrayList<Song>) getIntent().getSerializableExtra("ArraySong");
        // gọi đến cái CustomAdapter
        adapter = new CustomAdapter((ArrayList<Song>) arraySongs);

        // lấy dữ liệu bài hát ở vị trí
        songs = arraySongs.get(position);
        textViewName.setText(songs.getTitle());
        imageView2.setImageResource(songs.getImage());
        khoiTao();


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
                    SetTimeTotal();
                    capNhatThoiGianBaiHat();
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
                imageView2.startAnimation(animation);
                mediaPlayer.start();
                SetTimeTotal();
                capNhatThoiGianBaiHat();


            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 0 ) {
                    position = arraySongs.size() - 1;
                    songs = arraySongs.get(position);
                }else {
                    position--;
                    songs = arraySongs.get(position);
                }

                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    imageView2.clearAnimation();
                }
                databases.TTBaiHat();
                khoiTao();
                imageView2.startAnimation(animation);
                mediaPlayer.start();
                SetTimeTotal();
                capNhatThoiGianBaiHat();
            }
        });


        btnStop.setOnClickListener((view) -> {

//            if (mediaPlayer.isPlaying()) {
                imageView2.clearAnimation();
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.ic_play);
                khoiTao();
//            }
        });


        // bắt sự kiện trên SeekBak
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // chạm vào kéo seekBar xong buông ra thì nó sẽ lấy giá trị seekBar cuối cùng khi buông ra
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void capNhatThoiGianBaiHat(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                textViewTimeStart.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));

                //update progress skSong
                seekBar.setProgress(mediaPlayer.getCurrentPosition());


                //Kiểm tra thời gian bài hát -> nếu kết thúc  -> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
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
                        khoiTao();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.ic_pause);
                        SetTimeTotal();
                        capNhatThoiGianBaiHat();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }

    private void SetTimeTotal() {
        // hàm xử lý sự kiện phút và giây
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        textViewTimeEnd.setText(dinhDangGio.format(mediaPlayer.getDuration()));

        // gán SeeBak = tổng thời gian bài hát
        // tức là gán max của skSong = mediaPlayer.getDuration()
        seekBar.setMax(mediaPlayer.getDuration());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    private void khoiTao() {
        mediaPlayer = MediaPlayer.create(SongItemActivity.this, arraySongs.get(position).getFile());
        textViewName.setText(arraySongs.get(position).getTitle());
        textViewName.setText(songs.getTitle());
        imageView2.setImageResource(songs.getImage());
        btnPlay.setImageResource(R.drawable.ic_pause);
//        imageView2.startAnimation(animation);
        adapter.notifyDataSetChanged(); // lữu những thông tin thay đổi
    }

    private void init() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btnPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
        btnPlay = (ImageButton) findViewById(R.id.ButtonPlay);
        btnStop = (ImageButton) findViewById(R.id.imageButtonStop);
        btnNext = (ImageButton) findViewById(R.id.imageButtonNext);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewTimeStart = (TextView) findViewById(R.id.textViewStart);
        textViewTimeEnd = (TextView) findViewById(R.id.textViewEnd);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
    }

}