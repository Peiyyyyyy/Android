package com.example.peiyu.mynote;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.journeyfu.www.personal_journal.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JournallistActivity extends AppCompatActivity implements  View.OnClickListener{
    private MyDatabaseHelper Journal_dbHelper;
    private DrawerLayout mDrawerLayout;
    private EditText et_title;
    private static final int TAKE_PHOTO =1;
    private static final int CHOOSE_PHOTO =2;

    private EditText et_content;
    private int index = -1;
    private String username;
    private List<Journal> JournalList = new ArrayList<>();
    private ListView listView;
    private String datetime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.module_activity_journallist);
        //ListView 点击相应
        JournalAdapter adapter = new JournalAdapter(JournallistActivity.this,R.layout.module_list_item,JournalList);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Journal journal = JournalList.get(position);
                index = position;
                EditText et_title = (EditText) findViewById(R.id.et_title);
                EditText et_content = (EditText) findViewById(R.id.et_content);
                et_title.setText(journal.getTitle());
                et_content.setText(journal.getContent());
                datetime = journal.getTime();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        Intent intent = getIntent();
        String Username = intent.getStringExtra("Username");
        //获取滑栏头部的header控件

        TextView header_username = (TextView) findViewById(R.id.username);
        header_username.setText("用户: ".concat(Username));
        username = Username;
        ImageButton add_journal = (ImageButton) findViewById(R.id.btn_list_add);
        ImageButton delete_journal = (ImageButton) findViewById(R.id.btn_list_delete);
        ImageButton delete_journal_all = (ImageButton) findViewById(R.id.btn_list_delete_all);
        add_journal.setOnClickListener(this);
        delete_journal.setOnClickListener(this);
        delete_journal_all.setOnClickListener(this);
        //初始化
        initJournal();

        //侧栏滑动事件监听
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if(index != -1){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    String time = simpleDateFormat.format(date);
                    Journal_dbHelper = new MyDatabaseHelper(JournallistActivity.this,"MyDataBase.db",null,4);
                    SQLiteDatabase Journal_db = Journal_dbHelper.getWritableDatabase();
                    et_title = (EditText)  findViewById(R.id.et_title);
                    et_content = (EditText) findViewById(R.id.et_content);
                    String title = et_title.getText().toString();
                    String content = et_content.getText().toString();
                    ListView listView = (ListView) findViewById(R.id.list_item);
                    //插入
                    Journal_db.execSQL("update ContentTable set title = '"+title+"',content = '"+content+"' where time = datetime('"+time+"')");
                    JournalList.get(index).setTitle(et_title.getText().toString());
                    JournalList.get(index).setContent(et_content.getText().toString());
                    JournalAdapter adapter = new JournalAdapter(JournallistActivity.this,R.layout.module_list_item,JournalList);
                    listView.setAdapter(adapter);
                }
                et_title = (EditText)  findViewById(R.id.et_title);
                et_content = (EditText) findViewById(R.id.et_content);
                et_content.setEnabled(false);
                et_title.setEnabled(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                et_title = (EditText)  findViewById(R.id.et_title);
                et_content = (EditText) findViewById(R.id.et_content);
                et_content.setEnabled(true);
                et_title.setEnabled(true);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
    //onCreateOptionsMenu、onOptionsItemSelected用于处理Toolbar事件
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        et_title = (EditText)  findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        listView = (ListView) findViewById(R.id.list_item);
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_save://若当前索引为-1 则新加
                Journal_dbHelper = new MyDatabaseHelper(this,"MyDataBase.db",null,4);
                SQLiteDatabase Journal_db = Journal_dbHelper.getWritableDatabase();
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();
                if(index == -1){//插入
                    Journal_db.execSQL("insert into ContentTable values(datetime('"+time+"'),'"+username+"','"+title+"','"+content+"')");
                    Journal journal = new Journal(username,title,time,content);
                    JournalList.add(journal);
                    index = JournalList.size()-1;
                }else{//更新
                    Journal_db.execSQL("update ContentTable set title = '"+title+"',content = '"+content+"' where time = datetime('"+time+"')");
                    JournalList.get(index).setTitle(et_title.getText().toString());
                    JournalList.get(index).setContent(et_content.getText().toString());
                }
                JournalAdapter adapter = new JournalAdapter(JournallistActivity.this,R.layout.module_list_item,JournalList);
                listView.setAdapter(adapter);
                Toast.makeText(JournallistActivity.this,"已保存",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_clear://根据当前焦点的位置找到控件再清空
                View rootview = this.getWindow().getDecorView();
                int view = rootview.findFocus().getId();
                EditText et = (EditText) findViewById(view);
                et.setText("");
                break;
            case R.id.btn_logout:
                Intent intent = new Intent(JournallistActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
    //菜单栏的相应事件:添加一项、删除一项和删除所有
    @Override
    public void onClick(View view) {
        //获取时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        Journal_dbHelper = new MyDatabaseHelper(this,"MyDataBase.db",null,4);
        SQLiteDatabase Journal_db = Journal_dbHelper.getWritableDatabase();
        Cursor Journal_cursor;
        listView = (ListView) findViewById(R.id.list_item);
        et_title = (EditText)  findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        switch(view.getId()){
            case R.id.btn_list_add:
                if(index != -1) {//保存

                    Journal_db.execSQL("update ContentTable set title = '"
                            + et_title.getText().toString() + "',content = '" +
                            et_content.getText().toString() + "'"+" where time = datetime('"+time+"')");
                    JournalList.get(index).setTime(time);
                    JournalList.get(index).setTitle(et_title.getText().toString());
                    JournalList.get(index).setContent(et_content.getText().toString());
                }
                //插入新项
                Journal_db.execSQL("insert into ContentTable values(datetime('"+time+"'),'"+username+"','','')");
                Journal journal = new Journal(username,"",time,"");
                JournalList.add(journal);
                //变量更新
                index = JournalList.size()-1;
                datetime = time;
                et_title.setText("");
                et_content.setText("");
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btn_list_delete:
                if(index != -1) {
                    Journal_db.execSQL("delete from ContentTable where User_name = '" + username + "' and time = datetime('" + datetime + "')");
                    JournalList.remove(index);
                    et_title.setText("");
                    et_content.setText("");
                    index = -1;
                }
                break;
            case R.id.btn_list_delete_all:
                Journal_db.execSQL("delete from ContentTable where User_name = '" + username + "'");
                JournalList.clear();
                et_title.setText("");
                et_content.setText("");
                index = -1;
            default:
                break;
        }
        //更新
        JournalAdapter adapter = new JournalAdapter(JournallistActivity.this,R.layout.module_list_item,JournalList);
        listView.setAdapter(adapter);
    }
    //初始化 得到用户内容并布局
    private void initJournal(){
        Journal_dbHelper = new MyDatabaseHelper(this,"MyDataBase.db",null,4);
        SQLiteDatabase Journal_db = Journal_dbHelper.getReadableDatabase();
        Cursor Journal_cursor = Journal_db.rawQuery("select * from ContentTable where User_name = '".concat(username).concat("'"), null);
        JournalList.clear();
        if(Journal_cursor.moveToNext()) {//有内容
            do {
                String Title = Journal_cursor.getString(Journal_cursor.getColumnIndex("title"));
                String Time = Journal_cursor.getString(Journal_cursor.getColumnIndex("time"));
                String Content = Journal_cursor.getString(Journal_cursor.getColumnIndex("content"));
                Journal journal = new Journal(username,Title,Time,Content);
                JournalList.add(journal);
            } while (Journal_cursor.moveToNext());
        }
        Journal_cursor.close();
        JournalAdapter adapter = new JournalAdapter(JournallistActivity.this,R.layout.module_list_item,JournalList);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
    }

}
