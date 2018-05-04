package com.example.peiyu.mynote;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.journeyfu.www.personal_journal.R;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private Button btn_register_return;
    private Button btn_register_register;
    private String regex_forname = "^[a-zA-Z0-9_\\u4E00-\\u9FA5]+$";
    private String regex_forpsw = "^[a-zA-Z0-9_]+$";
    private EditText et_register_inputUsername;
    private EditText et_register_inputPassword;
    private EditText et_register_inputPassword_again;
    private MyDatabaseHelper register_dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        register_dbHelper = new MyDatabaseHelper(this,"MyDataBase.db",null,4);
        setContentView(R.layout.module_activity_register);
        et_register_inputUsername= (EditText)findViewById(R.id.et_register_inputUsername);
        et_register_inputPassword= (EditText)findViewById(R.id.et_register_inputPassword);
        et_register_inputPassword_again= (EditText)findViewById(R.id.et_register_inputPasswordagain);
        //返回登录界面事件
        btn_register_return = (Button)findViewById(R.id.btn_register_return);
        btn_register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_register_register= (Button)findViewById(R.id.btn_register_register);
        btn_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase register_db = register_dbHelper.getWritableDatabase();
                Cursor register_cursor = register_db.rawQuery("select * from UserTable where User_name = '".concat(et_register_inputUsername.getText().toString()).concat("'"), null);
                Pattern pattern = Pattern.compile(regex_forname);
                Pattern pattern2 = Pattern.compile(regex_forpsw);
                if (et_register_inputUsername.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "注册用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if(register_cursor.moveToFirst()){
                    Toast.makeText(RegisterActivity.this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
                    register_cursor.close();
                }else if (et_register_inputPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "注册密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (et_register_inputUsername.getText().toString().length() < 4 || et_register_inputUsername.getText().toString().length() > 10) {
                    Toast.makeText(RegisterActivity.this, "注册用户名长度在4-10位之间", Toast.LENGTH_SHORT).show();
                } else if (et_register_inputPassword.getText().toString().length() < 4 || et_register_inputPassword.getText().toString().length() > 10) {
                    Toast.makeText(RegisterActivity.this, "注册密码长度在4-10位之间", Toast.LENGTH_SHORT).show();
                } else if (!pattern.matcher(et_register_inputUsername.getText().toString()).matches()) {
                    Toast.makeText(RegisterActivity.this, "注册用户名只能包含汉字、字母、数字或'_'", Toast.LENGTH_SHORT).show();
                } else if (!pattern2.matcher(et_register_inputPassword.getText().toString()).matches()) {
                    Toast.makeText(RegisterActivity.this, "注册密码只能由包含字母、数字或'_'构成", Toast.LENGTH_SHORT).show();
                } else if(!et_register_inputPassword.getText().toString().equals(et_register_inputPassword_again.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "确认两次密码输入相同", Toast.LENGTH_SHORT).show();
                } else{
                    ContentValues values = new ContentValues();
                    values.put("User_name",et_register_inputUsername.getText().toString());
                    values.put("User_password",et_register_inputPassword.getText().toString());
                    register_db.insert("UserTable",null,values);
                    register_cursor.close();
                    values.clear();
                    Toast.makeText(RegisterActivity.this, "用户注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,JournallistActivity.class);
                    intent.putExtra("Username",et_register_inputUsername.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}

