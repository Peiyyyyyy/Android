package com.example.peiyu.calculatorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * 数字
     */
    private Button num0;
    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    private Button num5;
    private Button num6;
    private Button num7;
    private Button num8;
    private Button num9;

    /**
     * 运算符
     */
    private Button plus_btn;        //加
    private Button subtract_btn;    //减
    private Button multiply_btn;    //乘
    private Button divide_btn;      //除
    private Button equal_btn;       //等于
    //private Button oppsite_btn;     //取反

    /**
     * 其他
     */
    private Button dot_btn;         //小数点
    private Button percent_btn;     //百分号
    private Button delete_btn;      //删除
    private Button ac_btn;          //清零

    //结果
    private EditText resultText;

    //已输入的字符
    private  String editText;

    //是否计算过
    private boolean isCounted = false;

    //以负号开头，且运算符不是减号，例如：-2*2
    private boolean startWithOperator = false;

    //以负号开头，且运算符是减号，例如：-2-2
    private boolean startWithSubtract = false;

    //不以负号开头，且包含运算符，例如：2-2
    private boolean noStartWithOperator = false;

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

        initView();
        initEvent();
    }

    //初始化控件
    private void initView(){
        //数字
        num0 = (Button) findViewById(R.id.button_0);
        num1 = (Button) findViewById(R.id.button_1);
        num2 = (Button) findViewById(R.id.button_2);
        num3 = (Button) findViewById(R.id.button_3);
        num4 = (Button) findViewById(R.id.button_4);
        num5 = (Button) findViewById(R.id.button_5);
        num6 = (Button) findViewById(R.id.button_6);
        num7 = (Button) findViewById(R.id.button_7);
        num8 = (Button) findViewById(R.id.button_8);
        num9 = (Button) findViewById(R.id.button_9);

        //运算符及其他
        plus_btn = (Button) findViewById(R.id.button_plus);
        subtract_btn = (Button) findViewById(R.id.button_sub);
        multiply_btn = (Button) findViewById(R.id.button_mul);
        divide_btn = (Button) findViewById(R.id.button_div);
        equal_btn = (Button) findViewById(R.id.button_result);
        //oppsite_btn = (Button) findViewById(R.id.button_char);

        dot_btn = (Button) findViewById(R.id.button_dot);
        percent_btn = (Button) findViewById(R.id.button_per);
        delete_btn = (Button) findViewById(R.id.button_DEL);
        ac_btn = (Button) findViewById(R.id.button_C);

        //结果
        resultText = (EditText) findViewById(R.id.edit_input);

        //已输入的字符
        editText = resultText.getText().toString();
    }

    //初始化事件
    private void initEvent(){
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);

        plus_btn.setOnClickListener(this);
        subtract_btn.setOnClickListener(this);
        multiply_btn.setOnClickListener(this);
        divide_btn.setOnClickListener(this);
        equal_btn.setOnClickListener(this);
        //oppsite_btn.setOnClickListener(this);

        dot_btn.setOnClickListener(this);
        percent_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        ac_btn.setOnClickListener(this);
    }

    //点击事件
    public void onClick(View v){
        switch (v.getId()) {
            /**
             * 数字
             */
            case R.id.button_0:
                editText = isOverRange(editText, "0");
                break;
            case R.id.button_1:
                editText = isOverRange(editText, "1");
                break;
            case R.id.button_2:
                editText = isOverRange(editText, "2");
                break;
            case R.id.button_3:
                editText = isOverRange(editText, "3");
                break;
            case R.id.button_4:
                editText = isOverRange(editText, "4");
                break;
            case R.id.button_5:
                editText = isOverRange(editText, "5");
                break;
            case R.id.button_6:
                editText = isOverRange(editText, "6");
                break;
            case R.id.button_7:
                editText = isOverRange(editText, "7");
                break;
            case R.id.button_8:
                editText = isOverRange(editText, "8");
                break;
            case R.id.button_9:
                editText = isOverRange(editText, "9");
                break;
            /**
             * 运算符
             */
            case R.id.button_plus:
                /**
                 * 判断已有的字符是否是科学计数
                 * 是 置零
                 * 否 进行下一步
                 *
                 * 判断表达式是否可以进行计算
                 * 是 先计算再添加字符
                 * 否 添加字符
                 *
                 * 判断计算后的字符是否是 error
                 * 是 置零
                 * 否 添加运算符
                 */
                if (!editText.contains("e")) {

                    if (judgeExpression()) {
                        editText = getResult();
                        if (editText.equals("error")){

                        } else {
                            editText += "+";
                        }
                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((editText.substring(editText.length() - 1)).equals("-")) {
                            editText = editText.replace("-", "+");
                        } else if ((editText.substring(editText.length() - 1)).equals("×")) {
                            editText = editText.replace("×", "+");
                        } else if ((editText.substring(editText.length() - 1)).equals("÷")) {
                            editText = editText.replace("÷", "+");
                        } else if (!(editText.substring(editText.length() - 1)).equals("+")) {
                            editText += "+";
                        }
                    }
                } else {
                    editText = "0";
                }

                break;
            case R.id.button_sub:

                if (!editText.contains("e")) {
                    if (judgeExpression()) {
                        editText = getResult();
                        if (editText.equals("error")){

                        } else {
                            editText += "-";
                        }
                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((editText.substring(editText.length() - 1)).equals("+")) {
//                    Log.d("Anonymous", "onClick: " + "进入减法方法");
                            editText = editText.replace("+", "-");
                        } else if ((editText.substring(editText.length() - 1)).equals("×")) {
                            editText = editText.replace("×", "-");
                        } else if ((editText.substring(editText.length() - 1)).equals("÷")) {
                            editText = editText.replace("÷", "-");
                        } else if (!(editText.substring(editText.length() - 1)).equals("-")) {
                            editText += "-";
                        }
                    }
                } else {
                    editText = "0";
                }
                break;
            case R.id.button_mul:

                if (!editText.contains("e")) {
                    if (judgeExpression()) {
                        editText = getResult();
                        if (editText.equals("error")){

                        } else {
                            editText += "×";
                        }

                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((editText.substring(editText.length() - 1)).equals("+")) {
                            editText = editText.replace("+", "×");
                        } else if ((editText.substring(editText.length() - 1)).equals("-")) {
                            editText = editText.replace("-", "×");
                        } else if ((editText.substring(editText.length() - 1)).equals("÷")) {
                            editText = editText.replace("÷", "×");
                        } else if (!(editText.substring(editText.length() - 1)).equals("×")) {
                            editText += "×";
                        }
                    }
                } else {
                    editText = "0";
                }
                break;
            case R.id.button_div:

                if (!editText.contains("e")) {
                    if (judgeExpression()) {
                        editText = getResult();
                        if (editText.equals("error")){

                        } else {
                            editText += "÷";
                        }

                    } else {

                        if (isCounted) {
                            isCounted = false;
                        }

                        if ((editText.substring(editText.length() - 1)).equals("+")) {
                            editText = editText.replace("+", "÷");
                        } else if ((editText.substring(editText.length() - 1)).equals("-")) {
                            editText = editText.replace("-", "÷");
                        } else if ((editText.substring(editText.length() - 1)).equals("×")) {
                            editText = editText.replace("×", "÷");
                        } else if (!(editText.substring(editText.length() - 1)).equals("÷")) {
                            editText += "÷";
                        }
                    }
                } else {
                    editText = "0";
                }
                break;
            case R.id.button_result:
                editText = getResult();
                isCounted = true;
                break;
            /**
             * 其他
             */
            case R.id.button_dot:
                /**
                 * 判断是否运算过
                 * 否
                 *   判断是否有运算符，有 判断运算符之后的数字，无 判断整个数字
                 *   判断数字是否过长，是则不能添加小数点，否则可以添加
                 *   判断已经存在的数字里是否有小数点
                 * 是
                 *   字符串置为 0.
                 */
                if (!isCounted){

                    if (editText.contains("+") || editText.contains("-") ||
                            editText.contains("×") || editText.contains("÷") ){

                        String param1 = null;
                        String param2 = null;

                        if (editText.contains("+")) {
                            param1 = editText.substring(0, editText.indexOf("+"));
                            param2 = editText.substring(editText.indexOf("+") + 1);
                        } else if (editText.contains("-")) {
                            param1 = editText.substring(0, editText.indexOf("-"));
                            param2 = editText.substring(editText.indexOf("-") + 1);
                        } else if (editText.contains("×")) {
                            param1 = editText.substring(0, editText.indexOf("×"));
                            param2 = editText.substring(editText.indexOf("×") + 1);
                        } else if (editText.contains("÷")) {
                            param1 = editText.substring(0, editText.indexOf("÷"));
                            param2 = editText.substring(editText.indexOf("÷") + 1);
                        }
                        Log.d("Anonymous param1",param1);
                        Log.d("Anonymous param2",param2);

                        boolean isContainedDot = param2.contains(".");
                        if (param2.length() >= 9){

                        } else if (!isContainedDot){
                            if (param2.equals("")){
                                editText += "0.";
                            } else {
                                editText += ".";
                            }
                        } else {
                            return;
                        }
                    } else {
                        boolean isContainedDot = editText.contains(".");
                        if (editText.length() >= 9){

                        } else if (!isContainedDot){
                            editText += ".";
                        } else {
                            return;
                        }
                    }
                    isCounted = false;

                } else {
                    editText = "0.";
                    isCounted = false;
                }


                break;
            case R.id.button_per:
                /**
                 * 判断数字是否有运算符
                 * 是 不做任何操作
                 * 否 进行下一步
                 *
                 * 判断数字是否是 0
                 * 是 不做任何操作
                 * 否 进行除百
                 *
                 * 将字符串转换成double类型，进行运算后，再转换成String型
                 */
                if (editText.equals("error")){

                } else {

                    getCondition();

                    if (startWithOperator || startWithSubtract || noStartWithOperator) {

                    } else {
                        if (editText.equals("0")) {
                            return;
                        } else {
                            double temp = Double.parseDouble(editText);
                            temp = temp / 100;
                            editText = String.valueOf(temp);
                        }
                    }
                }
                break;
            case R.id.button_DEL:
                /**
                 * 字符串长度大于 0 时才截取字符串
                 * 如果长度为 1，则直接把字符串设置为 0
                 */
                if (editText.equals("error")){
                    editText = "0";
                } else if (editText.length() > 0){
                    if (editText.length() == 1) {
                        editText = "0";
                    } else {
                        editText = editText.substring(0,editText.length()-1);
                    }
                }
                break;
            case R.id.button_C:
                editText = "0";
                break;
        }
        /**
         * 设置显示
         */
        resultText.setText(editText);
    }
    private String getResult(){
        //结果
        String tempResult = null;

        String param1 = null;
        String param2 = null;

        //转换后的两个double类型参数
        double arg1 = 0;
        double arg2 = 0;
        double result = 0;

        getCondition();

        /**
         * 如果有运算符，则进行运算
         * 没有运算符，则把已经存在的数据再传出去
         */
        if ( startWithOperator || noStartWithOperator || startWithSubtract) {

            if (editText.contains("+")) {
                /**
                 * 先获取两个参数
                 */
                param1 = editText.substring(0, editText.indexOf("+"));
                param2 = editText.substring(editText.indexOf("+") + 1);
                /**
                 * 如果第二个参数为空，则还是显示当前字符
                 */
                if (param2.equals("")){
                    tempResult = editText;
                } else {
                    /**
                     * 转换String为Double
                     * 计算后再转换成String类型
                     * 进行正则表达式处理
                     */
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 + arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }


            } else if (editText.contains("×")) {

                param1 = editText.substring(0, editText.indexOf("×"));
                param2 = editText.substring(editText.indexOf("×") + 1);

                if (param2.equals("")){
                    tempResult = editText;
                } else {
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 * arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }

            } else if (editText.contains("÷")) {

                param1 = editText.substring(0, editText.indexOf("÷"));
                param2 = editText.substring(editText.indexOf("÷") + 1);

                if (param2.equals("0")){
                    tempResult = "error";
                } else if (param2.equals("")){
                    tempResult = editText;
                } else {
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 / arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }

            } else if (editText.contains("-")) {

                /**
                 * 这里是以最后一个 - 号为分隔去取出两个参数
                 * 进到这个方法，必须满足有运算公式
                 * 而又避免了第一个参数是负数的情况
                 */
                param1 = editText.substring(0, editText.lastIndexOf("-"));
                param2 = editText.substring(editText.lastIndexOf("-") + 1);

                if (param2.equals("")){
                    tempResult = editText;
                } else {
                    arg1 = Double.parseDouble(param1);
                    arg2 = Double.parseDouble(param2);
                    result = arg1 - arg2;
                    tempResult = String.format("%f", result);
                    tempResult = subZeroAndDot(tempResult);
                }

            }
            /**
             * 如果数据长度大于等于10位，进行科学计数
             *
             * 如果有小数点，再判断小数点前是否有十个数字，有则进行科学计数
             */
            if (tempResult.length() >= 10) {
                tempResult = String.format("%e", Double.parseDouble(tempResult));
            } else if (tempResult.contains(".")) {
                if (tempResult.substring(0, tempResult.indexOf(".")).length() >= 10) {
                    tempResult = String.format("%e", Double.parseDouble(tempResult));
                }
            }
        } else {
            tempResult = editText;
        }

        return tempResult;
    }

    /**
     * 先判断是否按过等于号
     * 是 按数字则显示当前数字
     * 否 在已有的表达式后添加字符
     *
     * 判断数字是否就是一个 0
     * 是 把字符串设置为空字符串。
     *   1、打开界面没有运算过的时候，C键归零或删除完归零的时候，会显示一个 0
     *   2、当数字是 0 的时候，设置成空字符串，再按 0 ，数字会还是 0，不然可以按出 000 这种数字
     * 否 添加按下的键的字符
     *
     * 判断数字是否包含小数点
     * 是 数字不能超过十位
     * 否 数字不能超过九位
     *
     * 进行上面的判断后，再判断数字是否超过长度限制
     * 超过不做任何操作
     * 没超过可以再添数字
     */
    private String isOverRange(String editText, String s) {
        /**
         * 判断是否计算过
         */
        if (!isCounted){
            /**
             * 判断是否是科学计数
             * 是 文本置零
             */
            if (editText.contains("e")){
                editText = "0";
            }
            /**
             * 判断是否只有一个 0
             * 是 文本清空
             */
            if (editText.equals("0")){
                editText = "";
            }
            /**
             * 判断是否有运算符
             * 是 判断第二个数字
             * 否 判断整个字符串
             */
            if (editText.contains("+") || editText.contains("-") ||
                    editText.contains("×") || editText.contains("÷")){
                /**
                 * 包括运算符时 两个数字 判断第二个数字
                 * 两个参数
                 */
                String param2 = null;
                if (editText.contains("+")){
                    param2 = editText.substring(editText.indexOf("+")+1);
                } else if (editText.contains("-")){
                    param2 = editText.substring(editText.indexOf("-")+1);
                } else if (editText.contains("×")){
                    param2 = editText.substring(editText.indexOf("×")+1);
                } else if (editText.contains("÷")){
                    param2 = editText.substring(editText.indexOf("÷")+1);
                }

//            Log.d("Anonymous param1",param1);
//            Log.d("Anonymous param2",param2);
                if (editText.substring(editText.length()-1).equals("+") ||
                        editText.substring(editText.length()-1).equals("-") ||
                        editText.substring(editText.length()-1).equals("×") ||
                        editText.substring(editText.length()-1).equals("÷")){
                    editText += s;
                } else {
                    if (param2.contains(".")){
                        if (param2.length() >= 10){

                        } else {
                            editText += s;
                        }
                    } else {
                        if (param2.length() >= 9){

                        } else {
                            editText += s;
                        }
                    }
                }
            } else {
                /**
                 * 不包括运算符时 一个数字
                 */
                if (editText.contains(".")){
                    if (editText.length() >= 10){

                    } else {
                        editText += s;
                    }
                } else {
                    if (editText.length() >= 9){

                    } else {
                        editText += s;
                    }
                }
            }

            isCounted = false;

        } else {

            editText = s;
            isCounted = false;

        }


        return editText;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s 传入的字符串
     * @return 修改之后的字符串
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 判断表达式
     *
     * 为了按等号是否进行运算
     * 以及出现两个运算符（第一个参数如果为负数的符号不计）先进行运算再添加运算符
     */
    private boolean judgeExpression() {

        getCondition();

        String tempParam2 = null;

        if ( startWithOperator || noStartWithOperator || startWithSubtract) {

            if (editText.contains("+")) {
                /**
                 * 先获取第二个参数
                 */
                tempParam2 = editText.substring(editText.indexOf("+") + 1);
                /**
                 * 如果第二个参数为空，表达式不成立
                 */
                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }
            } else if (editText.contains("×")) {

                tempParam2 = editText.substring(editText.indexOf("×") + 1);

                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }

            } else if (editText.contains("÷")) {

                tempParam2 = editText.substring(editText.indexOf("÷") + 1);

                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }

            } else if (editText.contains("-")) {

                /**
                 * 这里是以最后一个 - 号为分隔去取出两个参数
                 * 进到这个方法，必须满足有运算公式
                 * 而又避免了第一个参数是负数的情况
                 */
                tempParam2 = editText.substring(editText.lastIndexOf("-") + 1);

                if (tempParam2.equals("")) {
                    return false;
                } else {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 取得判断条件
     */
    private void getCondition() {
        /**
         * 以负号开头，且运算符不是是减号
         * 例如：-21×2
         */
        startWithOperator = editText.startsWith("-") && ( editText.contains("+") ||
                editText.contains("×") || editText.contains("÷") );
        /**
         * 以负号开头，且运算符是减号
         * 例如：-21-2
         */
        startWithSubtract = editText.startsWith("-") && ( editText.lastIndexOf("-") != 0 );
        /**
         * 不以负号开头，且包含运算符
         * 例如：21-2
         */
        noStartWithOperator = !editText.startsWith("-") && ( editText.contains("+") ||
                editText.contains("-") || editText.contains("×") || editText.contains("÷"));
    }

}


