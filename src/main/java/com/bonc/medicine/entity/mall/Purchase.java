package com.bonc.medicine.entity.mall;

public class Purchase {
	private int id; // 求购编号（创建时缺省）
	private String goods_name; // 商品名称
	private char purchase_cat_code; // 求购分类:1,种子种苗；2，源药材；3.农资农机
	private char goods_cat_code; // 商品品种：1肥料、2农机、3设备，4农药
	private String specification; // 规格：1 统货、2 皮、3 切片、4 两半
	private int user_id; // 用户编号
	private String img_url; // 商品图片
	private String publish_time; // 发布时间
	private char effect_flag; // 生效标识
	private int purchase_quantity; // 求购数量
	private String unit; // 求购数量的单位
	private String linkman; // 联系人
	private String linkman_phone; // 联系人电话
	private String attribute; // 特殊属性
	private String storehouse; // 库存地
	private String produce_area; // 商品产地
	private char receipt; // 可供票据
	private char quality_standard; // 质量标准
	private char send_sample; // 寄样
	private char payment; // 付款方式
	private char packing; // 包装
	private char qualification_standard; // 资质标准
	private char state; // 数据状态:0失效1能用
	private char is_aduit; // 审核状态:0未审核 1审核通过 2审核未通过
	private String comment; // 审核不通过的留言
	
	
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public char getPurchase_cat_code() {
		return purchase_cat_code;
	}
	public void setPurchase_cat_code(char purchase_cat_code) {
		this.purchase_cat_code = purchase_cat_code;
	}
	public char getGoods_cat_code() {
		return goods_cat_code;
	}
	public void setGoods_cat_code(char goods_cat_code) {
		this.goods_cat_code = goods_cat_code;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public char getEffect_flag() {
		return effect_flag;
	}
	public void setEffect_flag(char effect_flag) {
		this.effect_flag = effect_flag;
	}
	public char getReceipt() {
		return receipt;
	}
	public void setReceipt(char receipt) {
		this.receipt = receipt;
	}
	public char getQuality_standard() {
		return quality_standard;
	}
	public void setQuality_standard(char quality_standard) {
		this.quality_standard = quality_standard;
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
	public char getQualification_standard() {
		return qualification_standard;
	}
	public void setQualification_standard(char qualification_standard) {
		this.qualification_standard = qualification_standard;
	}
	public char getState() {
		return state;
	}
	public void setState(char state) {
		this.state = state;
	}
	public char getIs_aduit() {
		return is_aduit;
	}
	public void setIs_aduit(char is_aduit) {
		this.is_aduit = is_aduit;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	
	public int getPurchase_quantity() {
		return purchase_quantity;
	}
	public void setPurchase_quantity(int purchase_quantity) {
		this.purchase_quantity = purchase_quantity;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkman_phone() {
		return linkman_phone;
	}
	public void setLinkman_phone(String linkman_phone) {
		this.linkman_phone = linkman_phone;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getStorehouse() {
		return storehouse;
	}
	public void setStorehouse(String storehouse) {
		this.storehouse = storehouse;
	}
	public String getProduce_area() {
		return produce_area;
	}
	public void setProduce_area(String produce_area) {
		this.produce_area = produce_area;
	}
	
	
}
