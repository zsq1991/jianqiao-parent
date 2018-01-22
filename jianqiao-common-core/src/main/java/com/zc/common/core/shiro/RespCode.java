package com.zc.common.core.shiro;

/**
 * 返回抽象对象
 *
 * @author 郑朋
 * @create 2017/4/25
 **/
public interface RespCode {
    String getMsg();
    String getCode();

    enum Code implements RespCode {
        /**
         * 操作成功
         */
        SUCCESS("1","操作成功"),
        /**
         * 操作失败
         */
        FAIL("0","操作失败"),
        /**
         * 服务器内部错误
         */
        INTERNAL_SERVER_ERROR("500", "服务器内部错误"),
        /**
         * 没有权限访问！
         */
        PERMISSION_DENIED("403","没有权限访问！"),
        /**
         * 请求参数不全
         */
        REQUEST_DATA_ERROR("400","请求参数不全");
    	
        private String code;
        private String msg;

        Code(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @java.lang.Override
        @Override
        public String getMsg() {
            return msg;
        }

        @java.lang.Override
        @Override
        public String getCode() {
            return code;
        }
    }

}
