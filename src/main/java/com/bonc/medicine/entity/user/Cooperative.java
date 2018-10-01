package com.bonc.medicine.entity.user;

public class Cooperative {
	private String name;// 合作社名字
	private int official_user_id;// 负责人id
	private String official_user_name;// 负责人名字
	private String address;// 家庭住址
	private String img_url;// 合作社图片
	private String cultivar;// 覆盖品种
	private String establish_date;// 成立时间
	private String introduce;// 详细介绍

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getCultivar() {
		return cultivar;
	}

	public void setCultivar(String cultivar) {
		this.cultivar = cultivar;
	}

	public String getEstablish_date() {
		return establish_date;
	}

	public void setEstablish_date(String establish_date) {
		this.establish_date = establish_date;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
