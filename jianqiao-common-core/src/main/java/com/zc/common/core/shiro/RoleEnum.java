package com.zc.common.core.shiro;

import java.util.Objects;

/**
 * @描述： 角色编号枚举
 */
public enum RoleEnum {
    ADMIN((byte) 1, "管理员"),
    SUPPLIER((byte) 2, "一级管理"),;

    RoleEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    private Byte code;
    private String message;

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static RoleEnum getRoleEnumByCode(Byte code) {
        RoleEnum[] roleEnums =  RoleEnum.values();
        for (RoleEnum roleEnum : roleEnums) {
            if (Objects.equals(code, roleEnum.code)) {
                return roleEnum;
            }
        }
        return null;
    }


}
