package com.example.peiyu.calculatorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by PeiYu on 2018/4/12.
 */

public class UnitConvertion extends AppCompatActivity {


    private Spinner sp1, sp2;
    private EditText text;
    private TextView tv1, tv2, result;
    private String unit1, unit2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree);

        sp1 = (Spinner) findViewById(R.id.unit1);
        tv1 = (TextView) findViewById(R.id.text1);
        unit1 = (String) sp1.getSelectedItem();
        sp2 = (Spinner) findViewById(R.id.unit2);
        tv2 = (TextView) findViewById(R.id.text2);
        unit2 = (String) sp2.getSelectedItem();

        text = (EditText) findViewById(R.id.num1);
        result = (TextView) findViewById(R.id.num2);

        Button button = (Button) findViewById(R.id.convertion);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int pos, long id) {
                unit1 = (String) sp1.getSelectedItem();
                tv1.setText(unit1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing
            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int pos, long id) {
                unit2 = (String) sp2.getSelectedItem();
                tv2.setText(unit2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nothing
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = text.getText().toString();
                double i = Double.parseDouble(str);
                String s;

                if (unit1.equals("摄氏度")) {
                    switch (unit2) {
                        case "摄氏度":
                            result.setText(str);
                            break;
                        case "华氏度":
                            i = i * 1.8 + 32;
                            s = Double.toString(i);
                            result.setText(s);
                            break;
                        case "开尔文":
                            i = i + 273.15;
                            s = Double.toString(i);
                            result.setText(s);
                            break;
                    }
                } else if (unit1.equals("华氏度")) {
                    switch (unit2) {
                        case "摄氏度":
                            i = (i - 32) / 1.8;
                            s = Double.toString(i);
                            result.setText(s);
                            break;
                        case "华氏度":
//                    i = i * 1.8 + 32;
//                    s = Double.toString(i);
                            result.setText(str);
                            break;
                        case "开尔文":
                            i = (i + 459.67) / 1.8;
                            s = Double.toString(i);
                            result.setText(s);
                            break;
                    }
                }
                else{
                    switch (unit2) {
                        case "摄氏度":
                            i = i - 273.15;
                            s = Double.toString(i);
                            result.setText(s);
                            break;
                        case "华氏度":
                            i = i * 1.8 - 459.67;
                            s = Double.toString(i);
                            result.setText(s);
                            break;
                        case "开尔文":
//                    i = (i + 459.67) / 1.8;
//                    s = Double.toString(i);
                            result.setText(str);
                            break;
                    }
                }
            }
        });
    }



}
