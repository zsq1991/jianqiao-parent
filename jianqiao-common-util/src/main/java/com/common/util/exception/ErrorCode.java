package com.common.util.exception;

/**
 *@Author: Yaowei
 *@Description: 
 *@Date: 2017/11/27 15:53
 */
public enum ErrorCode {
    
    NULL_OBJ("001","请求参数为空或参数不全"),
    TYPE_ERROR("002","未知的参数类型"),
    UNKNOWN_ERROR("999","系统繁忙，请稍后再试....");

private String value;
private String desc;
private ErrorCode(String value, String desc) {
    this.setValue(value);
    this.setDesc(desc);
}

public String getValue() {
    return value;
}

public void setValue(String value) {
    this.value = value;
}

public String getDesc() {
    return desc;
}

public void setDesc(String desc) {
    this.desc = desc;
}

@Override
public String toString() {
    return "[" + this.value + "]" + this.desc;
}
}

