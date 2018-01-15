package com.common.util.exception;

/**
 *@Author: Yaowei
 *@Description: 
 *@Date: 2017/11/27 16:06
 */
public class BusinessException extends RuntimeException{
private static final long serialVersionUID = 1L;

public BusinessException(Object Obj) {
    super(Obj.toString());
}

}
