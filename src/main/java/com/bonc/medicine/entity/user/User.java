package com.bonc.medicine.entity.user;


import java.util.Date;

/**
 * 用户实体类，
 * @author hejiajun
 *
 */
public class User {

    //用户编号，自增长
    private int id;

    //用户姓名
    private String name;

    //用户微信账号
    private String wetchat;

    //用户密码
    private String password;

    //用户手机号码
    private String telephone;

    //用户邮箱
    private String email;

    //性别
    private char sex;

    //用户地址
    private String address;

    //用户生日
    private char birthDate;

    //有效状态（0：无效，1：有效）
    private char status;

    //更新时间
    private Date updateTime;

    //用户头像信息
    private String headPortrait;

    //用户角色id
    private String roles;
    //用户角色名字
    private String roleName;


    //用户关心品种
    private String caresVarieties;

    //验证码
    private String verification;
    private String employment_age;
    private String expertise_field;
    private String interactiveNumber;
    private String active_count;


    public String getActive_count() {
        return active_count;
    }

    public void setActive_count(String active_count) {
        this.active_count = active_count;
    }

    public String getInteractiveNumber() {
        return interactiveNumber;
    }

    public void setInteractiveNumber(String interactiveNumber) {
        this.interactiveNumber = interactiveNumber;
    }

    public String getEmployment_age() {
        return employment_age;
    }

    public void setEmployment_age(String employment_age) {
        this.employment_age = employment_age;
    }

    public String getExpertise_field() {
        return expertise_field;
    }

    public void setExpertise_field(String expertise_field) {
        this.expertise_field = expertise_field;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWetchat() {
        return wetchat;
    }

    public void setWetchat(String wetchat) {
        this.wetchat = wetchat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public char getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(char birthDate) {
        this.birthDate = birthDate;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getCaresVarieties() {
        return caresVarieties;
    }

    public void setCaresVarieties(String caresVarieties) {
        this.caresVarieties = caresVarieties;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wetchat='" + wetchat + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", headPortrait='" + headPortrait + '\'' +
                ", roles='" + roles + '\'' +
                ", roleName='" + roleName + '\'' +
                ", caresVarieties='" + caresVarieties + '\'' +
                ", verification='" + verification + '\'' +
                ", employment_age='" + employment_age + '\'' +
                ", expertise_field='" + expertise_field + '\'' +
                ", interactiveNumber='" + interactiveNumber + '\'' +
                ", active_count='" + active_count + '\'' +
                '}';
    }
}
