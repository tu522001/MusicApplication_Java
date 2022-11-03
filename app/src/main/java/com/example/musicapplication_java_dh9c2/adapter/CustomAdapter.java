package com.example.musicapplication_java_dh9c2.adapter;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication_java_dh9c2.R;
import com.example.musicapplication_java_dh9c2.Song;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Song> listSong;
    private static OnItemClickListener onItemClickListener;
    private MediaPlayer mediaPlayer;

    public CustomAdapter(ArrayList<Song> listSong) {
        this.listSong = listSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = listSong.get(position);
        holder.bind(listSong.get(position));
        holder.singerName.setText(song.getSinger());
        holder.imageView.setImageResource(song.getImage());
        Log.d("WWW", String.valueOf(position));


        mediaPlayer = MediaPlayer.create(holder.imageView.getContext(), listSong.get(position).getFile());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Position : "+song.getTitle(), Toast.LENGTH_SHORT).show();
                if (mediaPlayer.isPlaying()) {
                    // Nếu đang phát -> pause -> đổi hình play
                    mediaPlayer.pause();
//                    btnPlay.setImageResource(R.drawable.ic_play);
                } else {
                    // đang ngừng -> phát  -> đổi hình pause
//                    mediaPlayer.start();
                    Start(holder, holder.getAdapterPosition()); // // Do not treat position as fixed; only use immediately and call `holder.getAdapterPosition()` to look it up later
//                    btnPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        
    }

    private void Start(ViewHolder holder,int position) {
        mediaPlayer = MediaPlayer.create(holder.imageView.getContext(),listSong.get(position).getFile());
        mediaPlayer.start();
    }


    @Override
    public int getItemCount() {
        return listSong.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView songName,singerName;
        private ImageView imageView;
        private ImageButton imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            songName =  itemView.findViewById(R.id.songName);
            singerName =  itemView.findViewById(R.id.singerName);
            imageButton =  itemView.findViewById(R.id.imageButton);
            imageView =  itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Song song) {
            songName.setText(song.getTitle());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
