package com.example.peiyu.startactivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Button button = (Button) findViewById(R.id.buttonReturn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnotherActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
