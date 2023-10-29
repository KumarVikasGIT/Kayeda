package com.kayeda.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.kayeda.app.Config;
import com.kayeda.app.Model.Item;
import com.kayeda.app.R;
import com.kayeda.app.WebClient.ApiClient;
import com.kayeda.app.WebClient.WebService;
import com.kayeda.app.databinding.ActivitySinglePostBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePost extends AppCompatActivity {

    WebService service;
    private ActivitySinglePostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySinglePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        service= ApiClient.getClient().create(WebService.class);

        String blogID=getIntent().getStringExtra("blogId");
        getPost(blogID);

    }

    private void getPost(String blogID){
        Call<Item> call=service.getPost(blogID, Config.BLOG_KEY);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                binding.tvTitle.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });

    }
}