package com.bonc.medicine.entity.field;

import java.util.Date;

/**
 * @ClassName Field
 * @Description 田间信息实体类
 * @Author YQ
 * @Date 2018/8/31 16:04
 * @Version 1.0
 */
public class Field {
    private int id; // 编号  (新建时缺省   修改时需要)
    private String plant_address; //种植地址
    private String plant_type;//种植类型
    private String lati_long; // 经纬度
    private float plant_area; // 种植面积
    private Date registation_time; // 登记时间
    private int user_id; // 种植户编号
    private String user_name; // 用户名
    private String user_tel; // 用户电话
    private String coop_name; // 所属合作社
    private String is_end; // 是否完成   0  完成     1  未完成
    private String photo_url; // 地块照片
    private String pre_crop; // 地块前作物
    private String seed_source; // 种苗来源
    private String soil_type;  // 土壤类型
    private float altitude; // 海拔
    private String vegetation_type; // 植被类型（待补充）
    private String transmittance_type; // 林间透光度(待补充)
    private Date update_time; // 最新修改时间
    private String state; // 数据状态   0,可用；    1，不可用
    private int creator_id; // 创建者ID
    private String guide_flag; // 是否指导标识   0 是      1  不是

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlant_address() {
        return plant_address;
    }

    public void setPlant_address(String plant_address) {
        this.plant_address = plant_address;
    }

    public String getPlant_type() {
        return plant_type;
    }

    public void setPlant_type(String plant_type) {
        this.plant_type = plant_type;
    }

    public String getLati_long() {
        return lati_long;
    }

    public void setLati_long(String lati_long) {
        this.lati_long = lati_long;
    }

    public float getPlant_area() {
        return plant_area;
    }

    public void setPlant_area(float plant_area) {
        this.plant_area = plant_area;
    }

    public Date getRegistation_time() {
        return registation_time;
    }

    public void setRegistation_time(Date registation_time) {
        this.registation_time = registation_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getCoop_name() {
        return coop_name;
    }

    public void setCoop_name(String coop_name) {
        this.coop_name = coop_name;
    }

    public String getIs_end() {
        return is_end;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPre_crop() {
        return pre_crop;
    }

    public void setPre_crop(String pre_crop) {
        this.pre_crop = pre_crop;
    }

    public String getSeed_source() {
        return seed_source;
    }

    public void setSeed_source(String seed_source) {
        this.seed_source = seed_source;
    }

    public String getSoil_type() {
        return soil_type;
    }

    public void setSoil_type(String soil_type) {
        this.soil_type = soil_type;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public String getVegetation_type() {
        return vegetation_type;
    }

    public void setVegetation_type(String vegetation_type) {
        this.vegetation_type = vegetation_type;
    }

    public String getTransmittance_type() {
        return transmittance_type;
    }

    public void setTransmittance_type(String transmittance_type) {
        this.transmittance_type = transmittance_type;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public String getGuide_flag() {
        return guide_flag;
    }

    public void setGuide_flag(String guide_flag) {
        this.guide_flag = guide_flag;
    }
}
