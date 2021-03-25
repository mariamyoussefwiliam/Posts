package com.example.rx_java.UI;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rx_java.data.PostClient;
import com.example.rx_java.data.SqlHelpper;
import com.example.rx_java.pojo.PostModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {
    MutableLiveData<List<PostModel>> postsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> posts = new MutableLiveData<>();
    SqlHelpper Helpper;
   /* public void getPosts() {
        PostClient.getINSTANCE().getPosts().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                postsMutableLiveData.setValue(response.body());
                Log.d("client","thepostClient has problem"+response.body().get(0));
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                posts.setValue("errr");
            }
        });
    }*/




    public void getPosts() {
        Observable<List<PostModel>>observable=PostClient.getINSTANCE().getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observer<List<PostModel>> observer=new Observer<List<PostModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(List<PostModel> value) {
                postsMutableLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                    Log.d("PostModelView","onError"+e);
            }

            @Override
            public void onComplete() {

            }

        };
        observable.subscribe(observer);

    }


    /*public void insert_posts(ArrayList<PostModel> posts, Context context)
    {
        Helpper=new SqlHelpper(context);
        Helpper.insertRecord(posts);


    }*/
    public void insert_posts(ArrayList<PostModel> posts, Context context)
    {
        Helpper=new SqlHelpper(context);
//        Helpper.insertRecord(posts);
        Observable Observable2 = Helpper.insertRecord(posts);

        Completable completable = Observable2.ignoreElements();

        CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("PostModelView","onError"+e);
            }
        };

        completable.subscribe(completableObserver);


    }

    public void delete_posts_from_Database( Context context)
    {
        Helpper=new SqlHelpper(context);
        Helpper.deleteRecord();
    }

   /* public void get_posts_from_Database( Context context)
    {
        Helpper=new SqlHelpper(context);
        postsMutableLiveData.setValue( Helpper.getrecords());
    }*/
    public void get_posts_from_Database( Context context)
    {
        Helpper=new SqlHelpper(context);

        Observable<List<PostModel>>observable1=Observable.fromArray(Helpper.getrecords())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observer<List<PostModel>> observer1=new Observer<List<PostModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(List<PostModel> value) {
                postsMutableLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("PostModelView","onError"+e);
            }

            @Override
            public void onComplete() {

            }

        };
        observable1.subscribe(observer1);
    }




}
