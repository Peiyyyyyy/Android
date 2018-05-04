package com.example.peiyu.mynote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by FZA on 2018-4-28.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER_INFO ="create table UserTable (" +
            "id integer primary key autoincrement," +
            "User_name text not null," +
            "User_password text not null )";
    public static final String CREATE_CONTENT_INFO="create table ContentTable ("+
            "time datetime not null primary key,"+
            "User_name text not null,"+
            "title text,"+
            "content text)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_INFO);
        db.execSQL(CREATE_CONTENT_INFO);
        //Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists UserTable");
        db.execSQL("drop table if exists ContentTable");
        onCreate(db);
    }

}
