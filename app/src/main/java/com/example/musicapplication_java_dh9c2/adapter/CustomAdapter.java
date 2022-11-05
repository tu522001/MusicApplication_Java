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
import com.example.musicapplication_java_dh9c2.model.Song;
import com.example.musicapplication_java_dh9c2.data.MusicDAO;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static ArrayList<Song> listSong;
    private static MusicDAO dbHelper;
    private static OnItemClickListener onItemClickListener;
    private MediaPlayer mediaPlayer;

    private static String usernameID;

    public CustomAdapter(ArrayList<Song> listSong) {
        CustomAdapter.listSong = listSong;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
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
                Toast.makeText(view.getContext(), "Position : " + song.getTitle(), Toast.LENGTH_SHORT).show();
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });

    }


    private void Start(ViewHolder holder, int position) {
        mediaPlayer = MediaPlayer.create(holder.imageView.getContext(), listSong.get(position).getFile());
        mediaPlayer.start();
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }

    private int position;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songName, singerName;
        private ImageView imageView;
        private ImageButton imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View
            dbHelper = new MusicDAO(itemView.getContext());
            songName = itemView.findViewById(R.id.songName);
            singerName = itemView.findViewById(R.id.singerName);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageView = itemView.findViewById(R.id.imageView);
          //  itemView.setOnCreateContextMenuListener(this);

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



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        CustomAdapter.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public static String getUsernameID() {
        return usernameID;
    }

    public static void setUsernameID(String usernameID) {
        CustomAdapter.usernameID = usernameID;
    }
}
