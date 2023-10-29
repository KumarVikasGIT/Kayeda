package com.kayeda.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kayeda.app.Adapter.HomePostsAdapter;
import com.kayeda.app.Config;
import com.kayeda.app.Interface.OnItemClick;
import com.kayeda.app.Model.BloggerPosts;
import com.kayeda.app.WebClient.ApiClient;
import com.kayeda.app.WebClient.WebService;
import com.kayeda.app.databinding.ActivityHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)

public class Home extends AppCompatActivity implements OnItemClick {

    private ActivityHomeBinding binding;
    private WebService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        service = ApiClient.getClient().create(WebService.class);

        binding.navigationView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                binding.progressBar.setVisibility(View.VISIBLE);

                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchPost(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

                return true;
            }
        });


        binding.rvPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchData();
    }

    void fetchData() {
        binding.progressBar.setVisibility(View.VISIBLE);

        Call<BloggerPosts> call = service.getPosts(Config.BLOG_KEY);
        call.enqueue(new Callback<BloggerPosts>() {
            @Override
            public void onResponse(Call<BloggerPosts> call, Response<BloggerPosts> response) {
                if (binding.progressBar.isAnimating())
                    binding.progressBar.setVisibility(View.GONE);

                if (response.code() == 200) {
                    if (response.body().getItems() != null) {

                        binding.llNoResponse.setVisibility(View.GONE);
                        binding.rvPosts.setVisibility(View.VISIBLE);
                        binding.rvPosts.setAdapter(new HomePostsAdapter(Home.this, response.body().getItems(), Home.this));
                        binding.rvPosts.getAdapter().notifyDataSetChanged();
                    } else {
                        binding.rvPosts.setVisibility(View.GONE);
                        binding.llNoResponse.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.rvPosts.setVisibility(View.GONE);
                    binding.llNoResponse.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BloggerPosts> call, Throwable t) {

            }
        });
    }


    void searchPost(String s) {
        binding.progressBar.setVisibility(View.VISIBLE);

        Call<BloggerPosts> call = service.searchPost(s, Config.BLOG_KEY);
        call.enqueue(new Callback<BloggerPosts>() {
            @Override
            public void onResponse(Call<BloggerPosts> call, Response<BloggerPosts> response) {
                if (binding.progressBar.isAnimating())
                    binding.progressBar.setVisibility(View.GONE);

                if (response.code() == 200) {
                    if (response.body().getItems() != null) {

                        binding.llNoResponse.setVisibility(View.GONE);
                        binding.rvPosts.setVisibility(View.VISIBLE);
                        binding.rvPosts.setAdapter(new HomePostsAdapter(Home.this, response.body().getItems(), Home.this));
                        binding.rvPosts.getAdapter().notifyDataSetChanged();
                    } else {
                        binding.rvPosts.setVisibility(View.GONE);
                        binding.llNoResponse.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.rvPosts.setVisibility(View.GONE);
                    binding.llNoResponse.setVisibility(View.VISIBLE);
                }


            }

            @Override

            public void onFailure(Call<BloggerPosts> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSinglePost(int Position, String blogId) {
        Intent toSinglePost = new Intent(Home.this, ChapterReader.class);
        toSinglePost.putExtra("blogId", blogId);
        startActivity(toSinglePost);
    }
}