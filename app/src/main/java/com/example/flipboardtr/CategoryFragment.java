package com.example.flipboardtr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    public static final String EXTRA_URL ="";

    String URLIS = "https://newsapi.org/v2/top-headlines?country=tr&category=business&apiKey=3f158235fff247fb8670a05240f3e3c9";
    String URLEGLENCE = "https://newsapi.org/v2/top-headlines?country=tr&category=entertainment&apiKey=3f158235fff247fb8670a05240f3e3c9";
    String URLGENEL = "https://newsapi.org/v2/top-headlines?country=tr&category=general&apiKey=3f158235fff247fb8670a05240f3e3c9";
    String URLSAGLIK = "https://newsapi.org/v2/top-headlines?country=tr&category=health&apiKey=3f158235fff247fb8670a05240f3e3c9";
    String URLBILIM = "https://newsapi.org/v2/top-headlines?country=tr&category=science&apiKey=3f158235fff247fb8670a05240f3e3c9";
    String URLSPOR = "https://newsapi.org/v2/top-headlines?country=tr&category=sports&apiKey=3f158235fff247fb8670a05240f3e3c9";
    String URLTEKNOLOJI = "https://newsapi.org/v2/top-headlines?country=tr&category=technology&apiKey=3f158235fff247fb8670a05240f3e3c9";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_category, container, false);

        FrameLayout isFrameLayout = RootView.findViewById(R.id.is_category_container);
        isFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getActivity(), CategoryHaber.class);
                detailIntent.putExtra(EXTRA_URL,URLIS);
                startActivity(detailIntent);
            }
        });

        FrameLayout eglenceFrameLayout = RootView.findViewById(R.id.eglence_category_container);
        eglenceFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getActivity(), CategoryHaber.class);
                detailIntent.putExtra(EXTRA_URL,URLEGLENCE);
                startActivity(detailIntent);
            }
        });


        FrameLayout saglikFrameLayout = RootView.findViewById(R.id.saglik_category_container);
        saglikFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getActivity(), CategoryHaber.class);
                detailIntent.putExtra(EXTRA_URL,URLSAGLIK);
                startActivity(detailIntent);
            }
        });

        FrameLayout bilimFrameLayout = RootView.findViewById(R.id.bilim_category_container);
        bilimFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getActivity(), CategoryHaber.class);
                detailIntent.putExtra(EXTRA_URL,URLBILIM);
                startActivity(detailIntent);
            }
        });

        FrameLayout sporFrameLayout = RootView.findViewById(R.id.spor_category_container);
        sporFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getActivity(), CategoryHaber.class);
                detailIntent.putExtra(EXTRA_URL,URLSPOR);
                startActivity(detailIntent);
            }
        });

        FrameLayout teknolojiFrameLayout = RootView.findViewById(R.id.teknoloji_category_container);
        teknolojiFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getActivity(), CategoryHaber.class);
                detailIntent.putExtra(EXTRA_URL,URLTEKNOLOJI);
                startActivity(detailIntent);
            }
        });


        return RootView;
    }


}

