package com.example.rx_java.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.rx_java.pojo.PostModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class SqlHelpper extends SQLiteOpenHelper {
    private static final int Database_version =1;
    public static final String database_name ="posts_database.db";
    public static final String table_name ="posts_table";
    public static final String column_Id ="ID";
    public static final String column_User ="User_Id";
    public static final String column_Title ="Title";
    public static final String column_Body ="Body";

    private SQLiteDatabase database;
    Context con=null;
    public SqlHelpper(Context context){

        super(context,database_name,null,Database_version);
        con=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  " + table_name + " ( " + column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + column_User + " INTEGER ," + column_Title + " VARCHAR ," +column_Body + " VARCHAR ); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table IF Exists ' " + table_name + " ' ;");
        onCreate(db);
    }


    //method1_Insert
    public Observable insertRecord(ArrayList<PostModel> posts)
    {
        database = this.getWritableDatabase();
        try{
            for(int i=0;i<posts.size();i++) {
                PostModel post = new PostModel();
                post = posts.get(i);
                ContentValues values = new ContentValues();
                values.put(this.column_User, post.getUserId());
                values.put(this.column_Title, post.getTitle());
                values.put(this.column_Body, post.getBody());
                database.insert(table_name, null, values);
            }
            database.close();
            Toast.makeText(con,"PostsSize"+posts.size(), Toast.LENGTH_LONG).show();
        }catch(Exception e) {
            Toast.makeText(con,"Failed",Toast.LENGTH_LONG).show();
        }

        return null;
    }

    public Completable deleteRecord()
    {
        try {
            database = this.getReadableDatabase();
            database.delete(table_name, null, null);
            database.close();
        }catch (Exception e){
            Log.d("delete","error with delete"+e);
        }
        return null;
    }

    //methode1
    public List<PostModel> getrecords(){
        database=this.getReadableDatabase();
        Cursor cursor=database.query(table_name,null,null,null,null,null,null);
        List<PostModel>records=new ArrayList<PostModel>();
        PostModel post;
        if(cursor.getCount()>0)
        {
            for(int i=0;i< cursor.getCount();i++ ){
                cursor.moveToNext();
                post=new PostModel();
                post.setId(Integer.parseInt(cursor.getString(0)));
                post.setUserId(Integer.parseInt(cursor.getString(1)));
                post.setTitle(cursor.getString(2));
                post.setBody(cursor.getString(3));
                records.add(post);
            }
        }
        cursor.close();
        database.close();
        return  records;
    }

}
