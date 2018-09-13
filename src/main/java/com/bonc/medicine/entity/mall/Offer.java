package com.bonc.medicine.entity.mall;


public class Offer {
	private int id; // 编号
	private int purchase_id; // 求购编号
	private int price; // 报价
	private String publish_time; // 报价时间
	private int supply_quantity; // 供应数量
	private String inventory; // 库存地
	private String produce_area; // 药材产地
	private char quality_standard; // 质量标准
	private String img_url; // 药材图片
	private char receipt; // 可供票据
	private char qualification_standard; // 资质标准
	private char send_sample; // 寄样
	private char payment; // 付款方式
	private char packing; // 包装
	private String goods_source; // 货源说明
	private String linkman; // 联系人
	private String telephone; // 联系人电话
	private char state; // 数据状态:0失效1能用
	private int user_id; // 用户编号
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPurchase_id() {
		return purchase_id;
	}
	public void setPurchase_id(int purchase_id) {
		this.purchase_id = purchase_id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSupply_quantity() {
		return supply_quantity;
	}
	public void setSupply_quantity(int supply_quantity) {
		this.supply_quantity = supply_quantity;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getProduce_area() {
		return produce_area;
	}
	public void setProduce_area(String produce_area) {
		this.produce_area = produce_area;
	}
	public char getQuality_standard() {
		return quality_standard;
	}
	public void setQuality_standard(char quality_standard) {
		this.quality_standard = quality_standard;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public char getReceipt() {
		return receipt;
	}
	public void setReceipt(char receipt) {
		this.receipt = receipt;
	}
	public char getQualification_standard() {
		return qualification_standard;
	}
	public void setQualification_standard(char qualification_standard) {
		this.qualification_standard = qualification_standard;
	}
	public char getSend_sample() {
		return send_sample;
	}
	public void setSend_sample(char send_sample) {
		this.send_sample = send_sample;
	}
	public char getPayment() {
		return payment;
	}
	public void setPayment(char payment) {
		this.payment = payment;
	}
	public char getPacking() {
		return packing;
	}
	public void setPacking(char packing) {
		this.packing = packing;
	}
	public String getGoods_source() {
		return goods_source;
	}
	public void setGoods_source(String goods_source) {
		this.goods_source = goods_source;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public char getState() {
		return state;
	}
	public void setState(char state) {
		this.state = state;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	
}
