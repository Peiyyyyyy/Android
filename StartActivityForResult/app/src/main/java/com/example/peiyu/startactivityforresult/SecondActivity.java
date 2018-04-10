package com.example.peiyu.startactivityforresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by PeiYu on 2018/4/10.
 */

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        Integer id=intent.getIntExtra("age", 2015011290);
        Toast.makeText(this,name + "  " + id, Toast.LENGTH_LONG).show();

        Button bt = (Button) findViewById(R.id.buttonSecond);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                int id = intent.getIntExtra("ID",2015011290);
                intent.putExtra("result","姓名:" + name + " 学号:" + id);
                setResult(1,intent);
                finish();
            }
        });
    }
}
