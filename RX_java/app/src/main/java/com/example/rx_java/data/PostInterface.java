package com.example.rx_java.data;

import com.example.rx_java.pojo.PostModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
        @GET("posts")
       // public Call<List<PostModel>> getPosts ();
     public Observable<List<PostModel>> getPosts();


}
