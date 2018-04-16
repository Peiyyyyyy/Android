package com.example.peiyu.calculatorapp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Stack;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * Created by PeiYu on 2018/4/15.
 */

public class Calculator {
    private Stack<Character> optr = new Stack<Character>();
    private Stack<Double> opnd = new Stack<Double>() ;

    public Calculator(){
        optr.clear();
        opnd.clear();
        optr.push('#');
    };
    public String run(String str){
        char  op, theta;
        Boolean iRN = false;
        double val, a, b, num;
        int len = str.length(),state = 0 ,temp = 0;
        op = '#';
        int i =  0;
        Success sc = new Success();
        while(str.charAt(i)!='#' || op !='#'){
            val = 0;
            if(isdigit(str.charAt(i))){//是否是数字
                if(str.charAt(i) != (char)960){
                    do{
                        val = val*10+str.charAt(i)-48;
                        i++;
                    }while(i < len-1 && isdigit(str.charAt(i)));

                }else{
                    val = Math.PI;
                    i++;
                }
                opnd.push(val);
            }else {
                if (str.charAt(i) == '.' && isdigit(str.charAt(i + 1))) {//解决小数点
                    num = opnd.peek();
                    i++;
                    String p ="0.";
                    while (isdigit(str.charAt(i))) {
                        p = p + Character.toString(str.charAt(i));
                        i++;
                    }
                    opnd.pop();
                    opnd.push(num +Double.parseDouble(p));
                }
                temp = 0;
                if (lp(op) < rp(str.charAt(i))) {//当期字符优先级 大于 前一个字符优先级
                    if(str.charAt(i) == 's'||str.charAt(i) == 't'||str.charAt(i) == 'c'){
                        optr.push(str.charAt(i));
                        i += 3;
                    }else {
                        optr.push(str.charAt(i));
                        i++;
                    }
                } else if (lp(op) == rp(str.charAt(i))) {//优先级相等
                    if (optr.peek() == '(')
                        optr.pop();
                    i++;

                } else {//当期字符优先级 小于 前一个字符优先级
                    theta = optr.peek();
                    optr.pop();
                    b = opnd.peek();
                    opnd.pop();
                    if(theta != 's' && theta != 't'  && theta != 'c' ){
                        a = opnd.peek();
                        opnd.pop();
                    } else{
                        a = 0;
                    }
                    sc = operate(theta,a,b);
                    state = sc.getState();
                    val = sc.getValue();
                    if(!iRN)   iRN = sc.getIRNbool(); //无理数的情况
                    switch (state){
                        case 0:
                            opnd.push(val);
                            break;
                        case 1:
                            return "除零错误";
                        case 2:
                            return "tan无定义";
                        default:
                            break;
                    }
                }
            }
            op = optr.peek();
            if(i >= len) break;
        }

        num = opnd.peek();

        NumberFormat nFormat=NumberFormat.getNumberInstance();
        nFormat.setMaximumFractionDigits(8);
        if(iRN){
            return nFormat.format(num);
        }else if(Math.abs(num - (int)num) == 0)//如果是整数
            return Integer.toString((int)num);
        else
            return Double.toString(num);


    }
    public void clear() {
        optr.clear();
        opnd.clear();
        optr.push('#');
    };
    private int lp(char op){
        int result = 0;//优先级
        if (op == '+')    result = 3;
        else if (op == '-')    result = 3;
        else if (op == '*')  result = 5;
        else if (op == '÷')  result = 5;
        else if (op == '(') result = 1;
        else if (op == ')')  result = 7;
        else if (op == '#')  result = 0;
        else if (op == 's')  result = 5;
        else if (op == 't')  result = 5;
        else if (op == 'c')  result = 5;
        return result;
    }
    private int rp(char op){
        int result = 0;
        if (op == '+')    result = 2;
        else if (op == '-')   result = 2;
        else if (op == '*') result = 4;
        else if (op == '÷') result = 4;
        else if (op == '(') result = 7;
        else if (op == ')') result = 1;
        else if (op == '#') result = 0;
        else if (op == 's')  result = 6;
        else if (op == 't')  result = 6;
        else if (op == 'c')  result = 6;
        return result;
    }
    private Success operate(char ch, double num1,double num2){
        //实现两数退栈运算再进栈的操作
        Boolean isIRN= false;
        double n1 = num1,n2 = num2 ,result = 0;
        if(ch == '+'){
            result = n1 + n2;
        }else if(ch == '-'){
            result = n1 - n2;
        }else if(ch == '*'){
            result = n1 * n2;
        }else if(ch == '÷'){
            if(n2 == 0) return new Success(1, result,isIRN);
            result = n1 / n2;
            if(n1 == Math.PI || n2 == Math.PI ) isIRN = true;
            else  if(isIrrationalNumber(n1,n2)) isIRN = true;//判断若是无理数，输出时控制精度
        }else if(ch == 's'){//sin
            isIRN = true;
            result = Math.sin(n2);
        }else if(ch == 't'){//tan
            isIRN = true;
            if(n2 % (Math.PI/2)== 0){
                return new Success(2,result,isIRN);
            }
            result = Math.tan(n2);
        }else if(ch == 'c') {
            isIRN = true;
            result = Math.cos(n2);
        }
        return new Success(0,result,isIRN);
    }
    private Boolean isdigit(char c){
        if(( c>='0' && c<='9')|| c == (char)960) {
            return true;
        }
        return false;
    }
    private Boolean isIrrationalNumber(double num1,double num2){//num1是分子 num2是分母
        if(num1%num2 == 0) {//能被整除
            return false;
        }else {
            int num_gcd = 1;
            Double n1 =num1 , n2 = num2;//小数变为整数
            if(num2%2==0||num2%5==0||num2%10==0){
                return false;
            }
            while(Math.abs(n1 - n1.intValue()) > 1e-8 ||Math.abs(n2 - n2.intValue()) > 1e-8){
                n1 *= 10;
                n2 *= 10;
            }
            int n_1 = n1.intValue(),n_2 = n2.intValue();
            num_gcd = gcd( n_1,n_2);
            n_2 /= num_gcd;
            if(n_2%2==0||n_2%5==0||n_2%10==0){
                return false;
            }
        }
        return true;
    }
    private int gcd(int x, int y){//求两个整数最大公约数
        //防止输入为0，导致程序出错
        if(x == 0 || y == 0){return 0;}
        //添加一个判断保证x > y
        if(x < y){
            int temp = x;
            temp = y;
            y = x;
        }
        //算法实现
        if(x%y == 0){
            return y;
        }else
        {
            return gcd(y,x%y);
        }
    }
}