package com.example.peiyu.fileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.name);
        final EditText id = (EditText) findViewById(R.id.number);

        Button bt1 = (Button) findViewById(R.id.button_input);
        Button bt2 = (Button) findViewById(R.id.button_output);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream out = null;
                try{
                    FileOutputStream fileOutputStream =
                            openFileOutput("MyFileName.txt", MODE_PRIVATE);
                    out = new BufferedOutputStream(fileOutputStream);
                    String content = "学号: " + id.getText().toString() + "   姓名: "
                            + name.getText().toString();
                    try{
                        out.write(content.getBytes(StandardCharsets.UTF_8));
                        Toast.makeText(MainActivity.this, "导入信息成功", Toast.LENGTH_LONG).show();
                    }
                    finally{
                        if(out != null)
                            out.close();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedReader in = null;
                try{
                    FileInputStream fileInputStream =
                            openFileInput("MyFileName.txt");
                    in = new BufferedReader(new InputStreamReader(fileInputStream));
                    int c;
                    StringBuilder stringBuilder = new StringBuilder("");
                    try{
                        while (in.ready()){
                            stringBuilder.append(in.readLine());
                        }
                        Toast.makeText(MainActivity.this, stringBuilder.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                    finally {
                        if(in != null)
                            in.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
