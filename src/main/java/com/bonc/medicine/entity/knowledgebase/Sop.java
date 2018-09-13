package com.bonc.medicine.entity.knowledgebase;

import java.util.Date;

/**
 * @Description :  品种SOP种植标准
 * @Date : 2018.8.29
 */
public class Sop {
    private int id;//编号
    private int variety_id;//品种编号
    private Date record_time;//录入时间
    private int record_user_id;//录入人
    private char sop_type;//SOP标准类型     1.国家标准,2.地方标准
    private char record_status;//审核状态   1.待提交,2.审核中,3.已发布,4.审核不通过,5.已撤销
    private String fail_opinion;//审核不通过意见
    private Date update_time;//最新修改时间

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

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public int getRecord_user_id() {
        return record_user_id;
    }

    public void setRecord_user_id(int record_user_id) {
        this.record_user_id = record_user_id;
    }

    public char getSop_type() {
        return sop_type;
    }

    public void setSop_type(char sop_type) {
        this.sop_type = sop_type;
    }

    public char getRecord_status() {
        return record_status;
    }

    public void setRecord_status(char record_status) {
        this.record_status = record_status;
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
