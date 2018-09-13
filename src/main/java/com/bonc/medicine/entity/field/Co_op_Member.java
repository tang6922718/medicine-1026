package com.bonc.medicine.entity.field;

/**
 * @ClassName Co_op_Member
 * @Description 合作社成员
 * @Author YQ
 * @Date 2018/8/28 19:04
 * @Version 1.0
 */
public class Co_op_Member {
    private int id;  // 记录编号   新增社员时缺省   修改社员时需要
    private String name; //名字
    private String img_url; // 头像图片
    private String sex; // 性别： 0 男     1 女
    private int age; // 年龄
    private String telephone; // 联系电话
    private String address; // 地址
    private int plant_age; // 种植年龄
    private String plant_cat_id; // 种植类型
    private float plant_area; // 种植面积
    private int coop_id; // 合作社编号
    private String user_id; //社员编号：  如果社员为平台用户，则社员编号为用户ID；如果社员不是平台用户，则该字段为空
    private String assistant; // 助手（技术员）标识    0 是     1 不是
    private String state; // 数据是否可用： 0 可用   1 不可用（数据删除时至为1）

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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public int getPlant_age() {
        return plant_age;
    }

    public void setPlant_age(int plant_age) {
        this.plant_age = plant_age;
    }

    public String getPlant_cat_id() {
        return plant_cat_id;
    }

    public void setPlant_cat_id(String plant_cat_id) {
        this.plant_cat_id = plant_cat_id;
    }

    public float getPlant_area() {
        return plant_area;
    }

    public void setPlant_area(float plant_area) {
        this.plant_area = plant_area;
    }

    public int getCoop_id() {
        return coop_id;
    }

    public void setCoop_id(int coop_id) {
        this.coop_id = coop_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
