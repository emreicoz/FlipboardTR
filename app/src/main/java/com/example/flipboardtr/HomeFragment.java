package com.example.flipboardtr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class HomeFragment extends Fragment implements HaberAdapter.OnItemClickListener{
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_BASLIK = "baslik";
    public static final String EXTRA_KAYNAK = "kaynak";
    public static final String EXTRA_ICERIK = "icerik";
    public static final String EXTRA_SITEURL = "url";

    private RecyclerView mRecyclerView;
    private HaberAdapter mHaberAdapter;
    private ArrayList<Haber> mHaberList;
    private RequestQueue mRequestQueue;
    private String url;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView =  inflater.inflate(R.layout.fragment_home,container,false);

        mRecyclerView = RootView.findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String dill  = getString(R.string.title_dil);

        mHaberList = new ArrayList<>();
        url = "https://newsapi.org/v2/top-headlines?country="+dill+"&apiKey=3f158235fff247fb8670a05240f3e3c9";


        mRequestQueue = Volley.newRequestQueue(getActivity());
        parseJSON(url);


        swipeRefreshLayout = RootView.findViewById(R.id.swipe_refresh_layout2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);


            }
        });
        return RootView;
    }


    public void parseJSON(String url) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject article = jsonArray.getJSONObject(i);

                                String author = article.getString("author");
                                String title = article.getString("title");
                                String description = article.getString("description");
                                String url = article.getString("url");
                                String urlToImage = article.getString("urlToImage");
                                String publishedAt = article.getString("publishedAt");
                                String content = article.getString("content");

                                mHaberList.add(new Haber(author,title,description,url,urlToImage,publishedAt,content));
                                mHaberAdapter = new HaberAdapter(getActivity(),mHaberList);
                                mRecyclerView.setAdapter(mHaberAdapter);
                                mHaberAdapter.setOnItemClickListener(HomeFragment.this);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mRequestQueue.add(request);

    }


    @Override
    public void OnItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(),HaberDetayActivity.class);
        Haber clickedItem = mHaberList.get(position);

        detailIntent.putExtra(EXTRA_URL,clickedItem.getUrlToImage());
        detailIntent.putExtra(EXTRA_BASLIK,clickedItem.getTitlee());
        detailIntent.putExtra(EXTRA_KAYNAK,clickedItem.getAuthor());
        detailIntent.putExtra(EXTRA_ICERIK,clickedItem.getContent());
        detailIntent.putExtra(EXTRA_SITEURL,clickedItem.getUrl());

        startActivity(detailIntent);
    }
}


