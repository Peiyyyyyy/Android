package com.example.peiyu.calculatorapp;

/**
 * Created by PeiYu on 2018/4/15.
 */

public class Success {
    private int isSuccess;//状态标志位 0为成功 1为除零错 2是tan无定义
    private Double value;
    private Boolean irRationalNumber;
    Success(){}
    Success(int bool,Double num,Boolean rn){
        isSuccess = bool;
        value = num;
        irRationalNumber = rn;
    }
    public Double getValue(){
        return value;
    }
    public int getState(){  return isSuccess;  }
    public Boolean getIRNbool(){  return irRationalNumber;}
}
