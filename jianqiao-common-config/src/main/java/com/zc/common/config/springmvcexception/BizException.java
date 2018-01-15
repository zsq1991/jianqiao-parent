package com.zc.common.config.springmvcexception;


/**
 * ClassName:BaseErrorHandler <br/>
 * Function: TODO ADD FUNCTION. <br/> 此类用的场景不多！
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年9月3日 下午10:04:55 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class BizException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2128062111650408907L;
	
	private String msg;//错误消息
	protected String code;//错误代码
	
	public BizException(String msg, String code,Object...args) {
		super(String.format(msg,args));
		this.msg = msg;
		this.code = code;
	}
	
	public BizException(String msg, String code) {
		super();
		this.msg = msg;
		this.code = code;
	}

	public BizException() {
		super();
	}

	/**
	 * 实例化异常
	 * @param message
	 * @param args
	 * @return
	 * @author chang
	 */
	public BizException newInstance(String message, Object...args) {
		return new BizException(message,this.code,args);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
