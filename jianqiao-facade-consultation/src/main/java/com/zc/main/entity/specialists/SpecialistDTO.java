package com.zc.main.entity.specialists;

import java.io.Serializable;

/**
 * @package : com.alqsoft.entity.specialist
 * @progect : jianqiao-parent
 * @Description :咨询DTO
 * @author  by : 朱军
 * @Creation Date ：2018年01月09日14:29
 */
public class SpecialistDTO implements Serializable{

    private static final long serialVersionUID = -2612559752236538198L;
    private Long id;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SpecialistDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}
