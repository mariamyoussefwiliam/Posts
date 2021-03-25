package com.example.rx_java.data;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rx_java.pojo.PostModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostClient {
    private static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    private PostInterface postInterface;

    private static PostClient INSTANCE;

   /* public PostClient() {
        try{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postInterface = retrofit.create(PostInterface.class);
    }catch (Exception e ){
            Log.d("postclient","PostClient"+e);

        }
    }*/

     public PostClient() {
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl(BASE_URL)
                  .addConverterFactory(GsonConverterFactory.create())
                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                  .build();
          postInterface = retrofit.create(PostInterface.class);
      }

    public static PostClient getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new PostClient();
        }

        return INSTANCE;

    }

    /*public Call<List<PostModel>> getPosts(){
        return postInterface.getPosts();
    }*/
  public Observable<List<PostModel>> getPosts(){
        return (Observable<List<PostModel>>) postInterface.getPosts();
    }

}
