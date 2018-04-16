package com.example.peiyu.calculatorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener{

    int lb = 0,rb = 0;//左括号 右括号个数

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.degree:
                item.setIntent(new Intent(CalculatorActivity.this, UnitConvertion.class));
                break;
            case R.id.length:
                item.setIntent((new Intent(CalculatorActivity.this, LengthUnit.class)));
                break;
            case R.id.help_item:
                Toast.makeText(getApplicationContext(), "这是帮助", Toast.LENGTH_LONG).show();
                break;
            case R.id.exit_item:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button btn_zero= (Button) findViewById(R.id.btn_zero);
        Button btn_one = (Button) findViewById(R.id.btn_one);
        Button btn_two= (Button) findViewById(R.id.btn_two);
        Button btn_three = (Button) findViewById(R.id.btn_three);
        Button btn_four= (Button) findViewById(R.id.btn_four);
        Button btn_five = (Button) findViewById(R.id.btn_five);
        Button btn_six= (Button) findViewById(R.id.btn_six);
        Button btn_seven = (Button) findViewById(R.id.btn_seven);
        Button btn_eight= (Button) findViewById(R.id.btn_eight);
        Button btn_night = (Button) findViewById(R.id.btn_night);

        Button btn_equal= (Button) findViewById(R.id.btn_equal);
        Button btn_point = (Button) findViewById(R.id.btn_point);
        Button btn_plus = (Button) findViewById(R.id.btn_plus);
        Button btn_minus = (Button) findViewById(R.id.btn_minus);
        Button btn_multiplication= (Button) findViewById(R.id.btn_multiplication);
        Button btn_division = (Button) findViewById(R.id.btn_division);
        Button btn_Auto_Complete= (Button) findViewById(R.id.btn_Auto_Complete);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        Button btn_right_bracket= (Button) findViewById(R.id.btn_right_bracket);
        Button btn_left_bracket= (Button) findViewById(R.id.btn_left_bracket);
        Button btn_clear= (Button) findViewById(R.id.btn_clear);
        Button btn_sin = (Button) findViewById(R.id.btn_sin);
        Button btn_cos= (Button) findViewById(R.id.btn_cos);
        Button btn_tan= (Button) findViewById(R.id.btn_tan);
        Button btn_pai= (Button) findViewById(R.id.btn_pai);
        btn_zero.setOnClickListener(this);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_night.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multiplication.setOnClickListener(this);
        btn_division.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_right_bracket.setOnClickListener(this);
        btn_left_bracket.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_sin.setOnClickListener(this);
        btn_cos.setOnClickListener(this);
        btn_tan.setOnClickListener(this);
        btn_Auto_Complete.setOnClickListener(this);
        btn_pai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        TextView tv = (TextView) findViewById(R.id.tv_process);
        TextView tv_result = (TextView) findViewById(R.id.tv_result);
        char ch;
        int pai= 960;//pai的ascii
        String ch_last4="",str = tv.getText().toString();
        int length = tv.getText().length();

        if(!tv.getText().toString().isEmpty()){
            ch = tv.getText().charAt(tv.getText().length()-1);
            if(tv.getText().toString().length() >= 4)
                ch_last4 = str.substring(length-4,length);
        }else{
            ch = ' ';
        }
        switch (v.getId()){
            case R.id.btn_zero:

                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("0");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_one:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("1");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_two:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("2");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_three:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("3");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_four:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("4");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_five:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("5");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_six:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else  if(ch != ')')
                    tv.append("6");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_seven:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("7");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_eight:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("8");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_night:
                if(ch == (char)960)
                    tv_result.setText(Character.toString((char)960).concat("已为常量"));
                else if(ch != ')')
                    tv.append("9");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_delete:
                if(ch != ' ') {
                    if (ch_last4.equals("tan(") || ch_last4.equals("sin(") || ch_last4.equals("cos(") )
                    {
                        lb -= 1;
                        tv.setText(str.substring(0, length - 4));
                    }else if(ch == ')'){
                        rb -= 1;
                        tv.setText(str.substring(0,length - 1));
                    }else if(ch == '('){
                        lb -= 1;
                        tv.setText(str.substring(0,length - 1));
                    }else{
                        tv.setText(str.substring(0,length - 1));
                    }
                }else
                    tv_result.setText("无内容删除");
                break;
            case R.id.btn_point:
                if(!(ch >='0'&&ch <= '9') && ch != '.'
                        && ch != ')' && ch != (char)pai) {
                    tv.append(".");
                }else if(ch >= '0' && ch <= '9') {
                    int i = tv.getText().length()-1;
                    char c = tv.getText().charAt(i);
                    while(c >= '0'&& c <='9'&&i >0){
                        i--;
                        c = tv.getText().charAt(i);
                    }
                    if(c != '.'){
                        tv.append(".");
                    }
                }
                break;
            case R.id.btn_plus:
                if(ch == '-'||ch == '*'||ch == '÷'||ch == '+')
                    tv.setText(str.substring(0,length-1).concat("+"));
                else if(ch != '('&&ch != ' ')
                    tv.append("+");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_minus:
                if(ch == '-'||ch == '*'||ch == '÷'||ch == '+')
                    tv.setText(str.substring(0,length-1).concat("-"));
                else if(ch != '('&&ch != ' ')
                    tv.append("-");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_multiplication://乘号
                if(ch == '-'||ch == '*'||ch == '÷'||ch == '+')
                    tv.setText(str.substring(0,length-1).concat("*"));
                else if(ch != '('&&ch != ' ')
                    tv.append("*");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_division://除号
                if(ch == '-'||ch == '*'||ch == '÷'||ch == '+')
                    tv.setText(str.substring(0,length-1).concat("÷"));
                else if(ch != '('&&ch != ' ')
                    tv.append("÷");
                else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_left_bracket://左括号
                if(!(ch>='0' && ch<='9')&&ch != (char)960){
                    lb += 1;
                    tv.append("(");
                }else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_right_bracket://右括号
                if(ch != '(' && ch != '+' && ch != '-' && ch != '*' && ch != '÷'&& ch != ' ' && rb < lb){
                    rb += 1;
                    tv.append(")");
                }else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_sin:
                if(ch != ')'&&!(ch>='0' && ch<='9' )&&ch != '.'&&ch != (char)960){
                    tv.append("sin(");
                    lb += 1;
                }else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_cos:
                if(ch != ')'&&!(ch>='0' && ch<='9')&&ch != '.'&&ch != (char)960){
                    tv.append("cos(");
                    lb += 1;
                }else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_tan:
                if(ch != ')'&&!(ch>='0' && ch<='9')&&ch != '.'&&ch != (char)960) {
                    tv.append("tan(");
                    lb += 1;
                }else
                    tv_result.setText("输入有误");
                break;
            case R.id.btn_clear://清空
                if(ch == ' '){
                    tv_result.setText("已清空");
                }else{
                    tv.setText(null);
                    tv_result.setText(null);
                    lb = 0;
                    rb = 0;
                }
                break;
            case R.id.btn_pai:
                if(!(ch >= '0'&& ch <='9')&&ch!= '.'&&ch != (char)pai&&ch !=')')
                    tv.append(Character.toString((char)pai));
                break;
            case R.id.btn_Auto_Complete://自动补全
                if(rb < lb && (ch >= '0'&&ch <= '9'||ch == (char)pai)){
                    for(int i = 0;i < (lb-rb);i++)
                        tv.append(")");
                    rb  =  lb;
                }
                break;
            case R.id.btn_equal:
                if(rb != lb) tv_result.setText("左右括号不等");
                else if(ch == ' ') tv_result.setText("无内容输入");
                else if(ch == '+'||ch == '-'||ch == '*'||ch == '÷')  tv_result.setText("输入有误");
                else {
                    get_result(str);
                }
                break;
            default:
                break;
        }
    }
    public void get_result(String s){
        TextView tv_result = (TextView) findViewById(R.id.tv_result);
        Calculator cal = new Calculator();
        tv_result.setText(cal.run(s.concat("#")));

    }
}


