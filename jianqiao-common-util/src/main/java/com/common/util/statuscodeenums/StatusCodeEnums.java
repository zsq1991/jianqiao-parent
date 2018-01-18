package com.common.util.statuscodeenums;

/**
 * @author
 * @package : com.common.util.securitycode
 * @progect : jianqiao-parent
 * @Description :
 * @Created by : 三只小金
 * @Creation Date ：2018年01月17日11:36
 */
public enum StatusCodeEnums {

    SUCCESS(1,"成功"),
    SUCCESS_NO_DATA(2,"没有数据"),
    USER_INFO_ERROR(3,"用户信息异常"),
    ERROR(0,"接口调用失败"),
    ERROR_PARAM(-1,"参数异常");

    StatusCodeEnums(int code, String msg){
        this.code=code;
        this.msg=msg;
    }
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
