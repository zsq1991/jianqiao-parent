package com.common.util.exception;

/**
 *@Author: Yaowei
 *@Description: 
 *@Date: 2017/11/27 14:33
 */
public class GMException extends Exception{

private static final long serialVersionUID = 1L;

public GMException(Integer resultCode, String resultMessage, String errorDetail, Integer responseCode) {
    super("{"+"\""+"resultCode"+"\""+":"+"\""+resultCode+"\""+","+"\""+"resultMsg"+"\""+":"+
              "\""+resultMessage+"\""+","+"\""+"errDetail"+"\""+":"+"\""+errorDetail+"\""+","+
              "\""+"responseCode"+"\""+":"+"\""+responseCode+"\""+"}");
}

public GMException(Integer resultCode, String resultMessage, String errorDetail) {
    super("{"+"\""+"resultCode"+"\""+":"+"\""+resultCode+"\""+","+"\""+"resultMsg"+"\""+":"+
              "\""+resultMessage+"\""+","+"\""+"errDetail"+"\""+":"+"\""+errorDetail+"\""+"}");
}

public GMException(String msg) {
    super(msg);
}
}

