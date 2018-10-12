package com.bonc.medicine.entity.field;

import java.util.Date;

/**
 * @ClassName Co_op
 * @Description 合作社  合作社只能有一个管理员
 * @Author YQ
 * @Date 2018/8/28 10:51
 * @Version 1.0
 */
public class Co_op {
    private int id; //合作社编号 (新增时不传ID  修改时传ID)
    private String name;  //合作社名称
    private String introduce;  //合作社介绍
    private int total_num; // 合作社总人数
    private float total_area; //合作社种植总面积
    private String img_url;  //合作社图片
    private String portrait; // 合作社头像
    private String cultivar;  // 覆盖品种
    private int age;  // 合作社管理员年龄
    private String telephone; //管理员联系电话
    private String address;  //地址
    private int official_user_id;  //管理员ID
    private String official_user_name; //管理员姓名
    private Date establish_date;//合作社成立日期
    private String photo_url; // 合作社申请照片
    private String state; // 数据状态   0 可用    1 不可用
    private String is_audit; // 是否审核   0 已审核    1 未审核   2 审核不通过
    private String comment; //审核未通过的原因

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public float getTotal_area() {
        return total_area;
    }

    public void setTotal_area(float total_area) {
        this.total_area = total_area;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOfficial_user_id() {
        return official_user_id;
    }

    public void setOfficial_user_id(int official_user_id) {
        this.official_user_id = official_user_id;
    }

    public String getOfficial_user_name() {
        return official_user_name;
    }

    public void setOfficial_user_name(String official_user_name) {
        this.official_user_name = official_user_name;
    }

    public Date getEstablish_date() {
        return establish_date;
    }

    public void setEstablish_date(Date establish_date) {
        this.establish_date = establish_date;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIs_audit() {
        return is_audit;
    }

    public void setIs_audit(String is_audit) {
        this.is_audit = is_audit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
