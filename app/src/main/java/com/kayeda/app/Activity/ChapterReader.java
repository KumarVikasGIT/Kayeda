package com.kayeda.app.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kayeda.app.Adapter.SingleChapterAdapter;
import com.kayeda.app.Config;
import com.kayeda.app.Model.Item;
import com.kayeda.app.WebClient.ApiClient;
import com.kayeda.app.WebClient.WebService;
import com.kayeda.app.databinding.ActivityChapterReaderBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterReader extends AppCompatActivity {

    private ActivityChapterReaderBinding binding;
    private WebService service;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChapterReaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        service = ApiClient.getClient().create(WebService.class);

        binding.navigationView.setNavigationOnClickListener(v->
                this.finish());

        binding.rvPage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        String blogId = getIntent().getStringExtra("blogId");

        getPost(blogId);
    }

    private void getPost(String blogID) {
        Call<Item> call = service.getPost(blogID, Config.BLOG_KEY);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                extractImage(response.body().getContent());
                binding.navigationView.setTitle(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });

    }

    private void extractImage(String content) {

        Document doc = Jsoup.parse(content);
        Elements elements = doc.select("img");

        for (Element tag : elements) {
//            Log.d("Page", "extractImage: " + tag.attr("src"));
            list.add(tag.attr("src"));
        }

        binding.rvPage.setAdapter(new SingleChapterAdapter(ChapterReader.this, list));
    }

}