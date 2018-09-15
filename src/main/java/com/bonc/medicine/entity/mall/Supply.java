package com.bonc.medicine.entity.mall;

import java.util.Date;

public class Supply {

    private int id;//供应id   （修改供应时用； 新增供应时不用）
    private String goods_name; //商品名称
    private Character supply_cat_code; //供应分类   供应分类（后续新增在后面加）：1 种子种苗、2 源药材、3 农资农机
    private Character goods_cat_code; //商品品种
    private Double price; //价格
    private Character price_unit; //计价单位   1 公斤     2 斤     3 吨
    private int seller_id;  //用户ID
    private String img_url; //商品图片
    private String specification; //商品规格
    private int inventory; //库存量
    private Character inventory_unit; //库存量单位   1 公斤     2 斤     3 吨
    private int sales_volumn; //起售量
    private Character sales_volumn_unit; //起售量单位  1 公斤     2 斤     3 吨
    private String produce_area; //商品产地
    private String storehouse; //库存地
    private Character receipt; //票据    票据:  0 提供票据    1 不提供票据
    private String qualifications;//资质标准
    private Character quality_standard; //质量标准   质量标准：  0 无     1 GMP证书      2  GSP证书
    private Character send_sample; //是否寄样   寄样：  0 提供寄样    1 不提供寄样
    private Character payment; //付款方式   付款方式：  0  先付款   1  货到付款   3 可协调
    private Character packing; //包装方式   包装： 0 机压包   1 编织袋   2 纸箱   3  可协调
    private String detail; //详细描述
    private String attribute; // 属性
    private String tel; //联系电话
    private String linkman; //联系人姓名
    private Date public_time; // 发布日期
    private Character carriage_status; //上架状态   上架状态：0，未上架；1，上架；2，下架
    private Character state; //数据状态   数据状态：  0 可用   1 不可用（数据删除时）
//    private Character is_fine; // 是否精品推荐  是否精品推荐 ：  0 是    1 不是
//    private Character is_local; //是否湘九味推荐    是否湘九味推荐 ： 0 是   1  不是
    private Character is_audit; //是否审核：0未审核，1审核通过，2审核未通过
    private String comment;  //审核时备注信息

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Character getSupply_cat_code() {
        return supply_cat_code;
    }

    public void setSupply_cat_code(Character supply_cat_code) {
        this.supply_cat_code = supply_cat_code;
    }

    public Character getGoods_cat_code() {
        return goods_cat_code;
    }

    public void setGoods_cat_code(Character goods_cat_code) {
        this.goods_cat_code = goods_cat_code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Character getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(Character price_unit) {
        this.price_unit = price_unit;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public Character getInventory_unit() {
        return inventory_unit;
    }

    public void setInventory_unit(Character inventory_unit) {
        this.inventory_unit = inventory_unit;
    }

    public int getSales_volumn() {
        return sales_volumn;
    }

    public void setSales_volumn(int sales_volumn) {
        this.sales_volumn = sales_volumn;
    }

    public Character getSales_volumn_unit() {
        return sales_volumn_unit;
    }

    public void setSales_volumn_unit(Character sales_volumn_unit) {
        this.sales_volumn_unit = sales_volumn_unit;
    }

    public String getProduce_area() {
        return produce_area;
    }

    public void setProduce_area(String produce_area) {
        this.produce_area = produce_area;
    }

    public String getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(String storehouse) {
        this.storehouse = storehouse;
    }

    public Character getReceipt() {
        return receipt;
    }

    public void setReceipt(Character receipt) {
        this.receipt = receipt;
    }

    
    public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public Character getQuality_standard() {
        return quality_standard;
    }

    public void setQuality_standard(Character quality_standard) {
        this.quality_standard = quality_standard;
    }

    public Character getSend_sample() {
        return send_sample;
    }

    public void setSend_sample(Character send_sample) {
        this.send_sample = send_sample;
    }

    public Character getPayment() {
        return payment;
    }

    public void setPayment(Character payment) {
        this.payment = payment;
    }

    public Character getPacking() {
        return packing;
    }

    public void setPacking(Character packing) {
        this.packing = packing;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Date getPublic_time() {
        return public_time;
    }

    public void setPublic_time(Date public_time) {
        this.public_time = public_time;
    }

    public Character getCarriage_status() {
        return carriage_status;
    }

    public void setCarriage_status(Character carriage_status) {
        this.carriage_status = carriage_status;
    }

    public Character getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }

    public Character getIs_audit() {
        return is_audit;
    }

    public void setIs_audit(Character is_audit) {
        this.is_audit = is_audit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
