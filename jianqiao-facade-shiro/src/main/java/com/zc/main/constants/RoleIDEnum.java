package com.zc.main.constants;

/**
 * @项目：phshopping-facade-permission
 * @描述： 角色ID枚举
 * @作者： Mr.zheng
 * @创建时间：2017-03-14
 * @Copyright @2017 by Mr.zheng
 */
public enum RoleIDEnum {
    /**
     * 管理员
     */
    ADMIN(1L,"管理员"),
    /**
     * 供应商
     */
    SUPPLIER(2L,"供应商"),
    /**
     * 供应商
     */
    CITY_AGENT(3L,"市级代理商"),
    /**
     * 县级代理商
     */
    COUNTY_AGENT(4L,"县级代理商"),
    /**
     * 社区代理商
     */
    COMMUNITY_AGENT(5L,"社区代理商"),
    /**
     * 商户
     */
    MERCHANT(6L, "商户"),
    /**
     * 运营
     */
    OPERATOR(7L, "运营"),//运营和财务先写在这儿，根据你们权限的具体情况来设计
    /**
     * 财务
     */
    TREASURER(8L, "财务");

    RoleIDEnum(long id, String message) {
        this.id = id;
        this.message = message;
    }

    private long id;
    private String message;

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public static RoleIDEnum getRoleEnumByCode(long id) {
        RoleIDEnum[] roleEnums =  RoleIDEnum.values();
        for (RoleIDEnum roleEnum : roleEnums) {
            if (id == roleEnum.id) {
                return roleEnum;
            }
        }
        return null;
    }


}
