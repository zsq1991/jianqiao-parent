package com.zc.common.core.shiro;



/**
 * @项目：wholesale
 * @描述：错误码enum
 * @作者： Mr.chang
 * @创建时间：2017/6/23
 * @Copyright @2017 by Mr.chang
 */
public enum ErrorCodeEnum implements RespCode {

	SUCCESS("200","操作成功"),
    PARAMS_ERROR("1001", "参数不全或错误"),
    USER_NOT_EXISTS("1002", "会员不存在"),
    PWD_ERROR("1003","密码错误"),
    USER_REGISTER_ERROR("1004","注册会员错误"),
    USER_EXISTS("1005","会员已存在"),
    ORDER_EXISTS("1006","订单已存在"),
    ADD_ORDER_ERROR("1007","新增订单异常"),
    SCORE_RECORD_EXISTS("1008","积分记录已存在"),
    UPDATE_PHONE_ERROR("1009","修改手机号异常"),
    UPDATE_TRADE_PWD_ERROR("1009","设置支付密码异常"),
    
    NOT_AUTH("1010","请先实名认证"),
    IDANDBANKCARD_MISMATCHING("1011","身份与银行卡不匹配，请检查实名信息"),
    BANKCARD_BYBIND("1012","银行卡已被绑定,请重新填写"),
    NOT_BIND_BANK("1013","请先绑定银行卡"),
    IDCARD_BYAUTH("1014","身份证已被认证"),
    idcard_MISMATCHING("1015","姓名与证件编号不匹配"),
    SMS_CODE_ERROR("1016","验证码错误"),
    USER_BYAUTH("1014","该会员已实名认证"),

    SCHEDULE_STANDBY_ERROR("1018","定时任务待用转可用异常"),
    
    TOKEN_ERROR("1017","登录已过期,请重新登录");

    /**
     * 状态码
     */
    private String code;
    /**
     * 描述
     */
    private String msg;

    ErrorCodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
