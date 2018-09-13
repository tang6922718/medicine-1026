package com.bonc.medicine.entity.mall;

import java.util.Date;

public class Dyanimic {
    private int id; //编号
    private int dyn_cat_id; // 动态分类
    private String desciption; // 文字描述
    private String img_url; // 图片
    private String video_url; // 视频URL
    private String telephone; // 联系方式
    private int publish_user_id; // 发表人
    private Date publish_time; // 发表时间
    private char effect_flag; // 生效标识
    private String fail_opinion; // 审核不通过意见
    private Date update_time; // 修改时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDyn_cat_id() {
        return dyn_cat_id;
    }

    public void setDyn_cat_id(int dyn_cat_id) {
        this.dyn_cat_id = dyn_cat_id;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public char getEffect_flag() {
        return effect_flag;
    }

    public void setEffect_flag(char effect_flag) {
        this.effect_flag = effect_flag;
    }

    public String getFail_opinion() {
        return fail_opinion;
    }

    public void setFail_opinion(String fail_opinion) {
        this.fail_opinion = fail_opinion;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(int publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public Dyanimic(int id, int dyn_cat_id, String desciption, String img_url, String video_url, String telephone, int publish_user_id, Date publish_time, char effect_flag, String fail_opinion, Date update_time) {
        this.id = id;
        this.dyn_cat_id = dyn_cat_id;
        this.desciption = desciption;
        this.img_url = img_url;
        this.video_url = video_url;
        this.telephone = telephone;
        this.publish_user_id = publish_user_id;
        this.publish_time = publish_time;
        this.effect_flag = effect_flag;
        this.fail_opinion = fail_opinion;
        this.update_time = update_time;
    }

    public Dyanimic() {
        super();
    }

    @Override
    public String toString() {
        return "Dyanimic{" +
                "id=" + id +
                ", dyn_cat_id=" + dyn_cat_id +
                ", desciption='" + desciption + '\'' +
                ", img_url='" + img_url + '\'' +
                ", video_url='" + video_url + '\'' +
                ", telephone='" + telephone + '\'' +
                ", publish_user_id=" + publish_user_id +
                ", publish_time=" + publish_time +
                ", effect_flag=" + effect_flag +
                ", fail_opinion='" + fail_opinion + '\'' +
                ", update_time=" + update_time +
                '}';
    }
}