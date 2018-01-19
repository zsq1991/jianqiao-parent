package com.zc.main.entity.doctor;

import com.zc.common.core.orm.hibernate.BaseIdEntity;
import com.zc.main.entity.specialists.SpecialistDTO;

import java.util.List;

/**
 * @package : com.alqsoft.entity.doctor
 * @className : Doctor
 * @description ：高手
 * @Created by  : 朱军
 * @Creation Date ： 2018/1/10 10:27
 * @version
 */
public class Doctor extends BaseIdEntity {

    private String headUrl;//头像

    private String phone;//账号

    private String hunterPhone;//项目服务专员账号

    private String name;//姓名

    private Integer sex;//性别 0 男 1 女

    private Integer age;//年龄

    private String  experience;//教育经历

    private String tel;//电话

    private String card;//身份证号

    private String address;//详细地址

    private String doctorDetail;//高手详情

    private Integer isDelete;//删除标记 0正常 1禁用

    private String longitude;//经度

    private String latitude;//维度

    private Long phTownId;// 乡镇 街道办事处编号

    private Long phProvinceId;//省编号

    private Long phCityId;//市编号

    private Long phCountyId;//区县编号

    private String provinceName;//省

    private String cityName;//市

    private String countyName;//县

    private String townName;//乡镇

    private Integer servicePackage;// 是否含有服务包 0不含有 1含有

    private Integer status;// 0存在 1删除

    private String bankCard;//银行卡号

    private String bankName;//银行名称

    /**
     * 用于返回数据
     */
    private String title; //咨询标题

    private List<SpecialistDTO> addressList;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getHunterPhone() {
        return hunterPhone;
    }

    public void setHunterPhone(String hunterPhone) {
        this.hunterPhone = hunterPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoctorDetail() {
        return doctorDetail;
    }

    public void setDoctorDetail(String doctorDetail) {
        this.doctorDetail = doctorDetail;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Long getPhTownId() {
        return phTownId;
    }

    public void setPhTownId(Long phTownId) {
        this.phTownId = phTownId;
    }

    public Long getPhProvinceId() {
        return phProvinceId;
    }

    public void setPhProvinceId(Long phProvinceId) {
        this.phProvinceId = phProvinceId;
    }

    public Long getPhCityId() {
        return phCityId;
    }

    public void setPhCityId(Long phCityId) {
        this.phCityId = phCityId;
    }

    public Long getPhCountyId() {
        return phCountyId;
    }

    public void setPhCountyId(Long phCountyId) {
        this.phCountyId = phCountyId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Integer getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(Integer servicePackage) {
        this.servicePackage = servicePackage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SpecialistDTO> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<SpecialistDTO> addressList) {
        this.addressList = addressList;
    }
}
