package com.bonc.medicine.entity.knowledgebase;

import java.util.Date;

/**
 * 知识库品种百科
 * Created by Administrator on 2018/8/29.
 */
public class VarietyEncyclopedia {
    private int id;// 编号
    private int variety_id;// 品种编号
    private char codex_status;// 药典状态(1.已导入,2.未导入)
    private Date record_time;// 登记时间
    private char record_status;// 状态(1.待提交,2.审核中,3.已发布,4.审核不通过,5.已撤销)
    private String variety_name;// 品种名称
    private String variety_alias;// 品种别名
    private Date product_time;// 产新时间
    private String property_flavor;// 性味
    private String distribution_area;// 产区分布
    private String effect;// 功效
    private String fail_opinion;// 审核不通过意见
    private Date update_time;// 修改时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(int variety_id) {
        this.variety_id = variety_id;
    }

    public char getCodex_status() {
        return codex_status;
    }

    public void setCodex_status(char codex_status) {
        this.codex_status = codex_status;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public char getRecord_status() {
        return record_status;
    }

    public void setRecord_status(char record_status) {
        this.record_status = record_status;
    }

    public String getVariety_name() {
        return variety_name;
    }

    public void setVariety_name(String variety_name) {
        this.variety_name = variety_name;
    }

    public String getVariety_alias() {
        return variety_alias;
    }

    public void setVariety_alias(String variety_alias) {
        this.variety_alias = variety_alias;
    }

    public Date getProduct_time() {
        return product_time;
    }

    public void setProduct_time(Date product_time) {
        this.product_time = product_time;
    }

    public String getProperty_flavor() {
        return property_flavor;
    }

    public void setProperty_flavor(String property_flavor) {
        this.property_flavor = property_flavor;
    }

    public String getDistribution_area() {
        return distribution_area;
    }

    public void setDistribution_area(String distribution_area) {
        this.distribution_area = distribution_area;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
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
}
