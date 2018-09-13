package com.bonc.medicine.entity.user;

public class Cooperative {
	private String name;
	private int official_user_id;
	private String official_user_name;
	private String address;
	private String img_url;
	private String cultivar;

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

}
