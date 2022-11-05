package com.example.musicapplication_java_dh9c2.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.musicapplication_java_dh9c2.songactivity.FavouriteActivity;
import com.example.musicapplication_java_dh9c2.useractivity.LoginActivity;
import com.example.musicapplication_java_dh9c2.R;

public class HomeFragment extends Fragment {
    TextView tvUsername;
    Button btnLogout;
    LinearLayout favoritesList;
    public static final String SHARED_PREFS = "user_prefs";
    public static final String USERNAME_KEY = "username_key";
    SharedPreferences sharedpreferences;
    String user;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);


        onclick();

        return view;
    }

    private void initView(View v) {
        tvUsername = v.findViewById(R.id.user_name_hf);
        btnLogout = v.findViewById(R.id.btn_user_logout);
        favoritesList = v.findViewById(R.id.h_favorite_list);

        sharedpreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(USERNAME_KEY, null);
        tvUsername.setText(user);
    }

    private void onclick() {
        btnLogout.setOnClickListener((view) -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
            dialog.setTitle("Đăng xuất").setMessage("Bạn có muốn đăng xuất?");
            dialog.setPositiveButton("Không", (d, w) -> {
                        d.dismiss();})
                    .setNegativeButton("Có", (d, w) -> {
                        Intent intent = new Intent(requireActivity(), LoginActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    });
            dialog.create().show();
        });

        favoritesList.setOnClickListener((v) -> {
            Intent favorIntent = new Intent(requireActivity(), FavouriteActivity.class);
            favorIntent.putExtra("username", user.toString());
            startActivity(favorIntent);
        });

    }


}