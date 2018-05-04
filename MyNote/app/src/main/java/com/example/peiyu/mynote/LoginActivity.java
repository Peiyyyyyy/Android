package com.example.peiyu.mynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.journeyfu.www.personal_journal.R;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox RememberPsw;
    private EditText Username;
    private EditText Psw;
    private Button btn_login_login;//按钮_登录界面_登录按钮
    private Button btn_login_register;
    private MyDatabaseHelper login_dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_login);
        login_dbHelper = new MyDatabaseHelper(this,"MyDataBase.db",null,4);//创建数据库，若已存在不创建
        RememberPsw = (CheckBox)findViewById(R.id.btn_save_info);
        Username = (EditText)findViewById(R.id.et_inputUsername);
        Psw = (EditText)findViewById(R.id.et_inputPassword);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("Remember_password",false);//第二项为默认值
        if(isRemember){
            Username.setText(pref.getString("Username",""));
            Psw.setText(pref.getString("Psw",""));
            RememberPsw.setChecked(true);
        }
        //注册事件
        btn_login_register = (Button)findViewById(R.id.btn_register);
        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //登录事件
        btn_login_login = (Button)findViewById(R.id.btn_login);
        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断输入合法性并查询用户表数据库
                SQLiteDatabase login_db = login_dbHelper.getReadableDatabase();
                Cursor login_cursor = login_db.rawQuery("SELECT * FROM UserTable WHERE User_name = '".concat(Username.getText().toString()).concat("'"),null);
                if (Username.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (Psw.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!login_cursor.moveToFirst()) {
                    Toast.makeText(LoginActivity.this, "用户名:" + Username.getText().toString() + " 不存在", Toast.LENGTH_SHORT).show();
                    login_cursor.close();
                }else if(!login_cursor.getString(login_cursor.getColumnIndex("User_password")).equals(Psw.getText().toString())){
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    login_cursor.close();
                }else{
                    login_cursor.close();
                    editor = pref.edit();
                    if (RememberPsw.isChecked()) {//记住密码
                        editor.putBoolean("Remember_password", true);
                        editor.putString("Username", Username.getText().toString());
                        editor.putString("Psw", Psw.getText().toString());
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, JournallistActivity.class);
                    intent.putExtra("Username",Username.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

}
