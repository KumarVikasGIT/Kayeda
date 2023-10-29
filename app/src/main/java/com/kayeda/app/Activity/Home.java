package com.kayeda.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kayeda.app.Adapter.HomePostsAdapter;
import com.kayeda.app.WebClient.ApiClient;
import com.kayeda.app.Config;
import com.kayeda.app.Interface.OnItemClick;
import com.kayeda.app.WebClient.WebService;
import com.kayeda.app.Model.BloggerPosts;
import com.kayeda.app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements OnItemClick {

    WebService service;
    RecyclerView rvBlogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvBlogs=findViewById(R.id.rvPosts);
        rvBlogs.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        service =ApiClient.getClient().create(WebService.class);



        fetchData();
    }

    void fetchData(){
        Call<BloggerPosts> call=service.getPosts(Config.BLOG_KEY);
        call.enqueue(new Callback<BloggerPosts>() {
            @Override
            public void onResponse(Call<BloggerPosts> call, Response<BloggerPosts> response) {
//                Log.d("RES", "onResponse: "+response.body().getKind().toString());
                rvBlogs.setAdapter(new HomePostsAdapter(Home.this,response.body().getItems(), Home.this));
            }

            @Override
            public void onFailure(Call<BloggerPosts> call, Throwable t) {

            }
        });
    }


    @Override
    public void onSinglePost(int Position, String blogId) {
        Intent toSinglePost =new Intent(Home.this, ChapterReader.class);
        toSinglePost.putExtra("blogId", blogId);
        startActivity(toSinglePost);
    }
}