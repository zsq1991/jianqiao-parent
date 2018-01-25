package com.common.util.exception;

/**
 *@author : Yaowei
 *@Description: 
 *@Date: 2017/11/27 14:33
 */
public class GmException extends Exception{

private static final long serialVersionUID = 1L;

public GmException(Integer resultCode, String resultMessage, String errorDetail, Integer responseCode) {
    super("{"+"\""+"resultCode"+"\""+":"+"\""+resultCode+"\""+","+"\""+"resultMsg"+"\""+":"+
              "\""+resultMessage+"\""+","+"\""+"errDetail"+"\""+":"+"\""+errorDetail+"\""+","+
              "\""+"responseCode"+"\""+":"+"\""+responseCode+"\""+"}");
}

public GmException(Integer resultCode, String resultMessage, String errorDetail) {
    super("{"+"\""+"resultCode"+"\""+":"+"\""+resultCode+"\""+","+"\""+"resultMsg"+"\""+":"+
              "\""+resultMessage+"\""+","+"\""+"errDetail"+"\""+":"+"\""+errorDetail+"\""+"}");
}

public GmException(String msg) {
    super(msg);
}
}

