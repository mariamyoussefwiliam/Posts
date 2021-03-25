package com.example.rx_java.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rx_java.R;
import com.example.rx_java.pojo.PostModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    PostViewModel postViewModel;
    Button api,insert,data,clear;
    TextView text;
    RecyclerView recyclerView;
    final PostAdapter adapter = new PostAdapter();
    ArrayList<PostModel> posts=new ArrayList<PostModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getallwidgits();
        listener();
    }

    private void getallwidgits(){

        api=(Button) findViewById(R.id.get_api);
        insert=(Button)findViewById(R.id.insert_data);
        data=(Button)findViewById(R.id.from_base);
        clear=(Button)findViewById(R.id.clearPosts);
        text=(TextView)findViewById(R.id.info);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        postViewModel = ViewModelProviders.of(MainActivity.this).get(PostViewModel.class);
    }
    private void listener(){
        api.setOnClickListener(this);
        insert.setOnClickListener(this);
        data.setOnClickListener(this);
        clear.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get_api:
                recyclerView.setVisibility(View.VISIBLE);
                apiWithevent();
                break;
            case R.id.insert_data:
                recyclerView.setVisibility(View.GONE);
                if(posts.size()>0){
                    insertPosts(posts,MainActivity.this);
                    posts.clear();}
                else{
                    text.setText("Get Data From API");
                }
                break;
            case R.id.from_base:
                databaseWithevent(MainActivity.this);
                posts.clear();
                break;
            case R.id.clearPosts:
                Clear_posts(MainActivity.this);
                break;
            default:
                recyclerView.setVisibility(View.GONE);
                break;
        }

    }



    private void apiWithevent(){
        postViewModel.getPosts();
        //   final PostsAdapter adapter = new PostsAdapter();
        recyclerView.setAdapter(adapter);
        postViewModel.postsMutableLiveData.observe(MainActivity.this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                adapter.setList(postModels);
                posts.addAll(postModels);
                text.setText("posts size = "+posts.size());

            }
        });

    }


    private void insertPosts(ArrayList<PostModel>posts, Context context){

        postViewModel.insert_posts(posts,context);
        text.setText("addedPostsSize= "+posts.size());

    }

    private void Clear_posts(Context context)
    {
        recyclerView.setVisibility(View.GONE);
        posts.clear();
        postViewModel.delete_posts_from_Database(context);
        text.setText("no data");
    }



    private void databaseWithevent(Context context)
    {
        recyclerView.setVisibility(View.VISIBLE);
        postViewModel.get_posts_from_Database(context);
        recyclerView.setAdapter(adapter);
        postViewModel.postsMutableLiveData.observe(MainActivity.this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                adapter.setList(postModels);
                text.setText("posts size = "+postModels.size());
            }
        });

    }

}