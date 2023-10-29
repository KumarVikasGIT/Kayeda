package com.kayeda.app.WebClient;

import com.kayeda.app.Model.BloggerPosts;
import com.kayeda.app.Model.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    @GET("posts")
    Call<BloggerPosts> getPosts(@Query("key") String key);

    @GET("posts/{postID}")
    Call<Item> getPost(@Path("postID") String id,
                       @Query("key") String key);
}
