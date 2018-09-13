package com.bonc.medicine.entity.knowledgebase;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/29.
 */
public class Audit {
    private int id;// 编号
    private String title;// 标题
    private char km_type;// 类型
    private int object_id;// 对象编号
    private Date publish_time;// 发布时间
    private char status;// 状态
    private String fail_opinion;// 审核不通过意见
    private Date audit_time;// 审核时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public char getKm_type() {
        return km_type;
    }

    public void setKm_type(char km_type) {
        this.km_type = km_type;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getFail_opinion() {
        return fail_opinion;
    }

    public void setFail_opinion(String fail_opinion) {
        this.fail_opinion = fail_opinion;
    }

    public Date getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(Date audit_time) {
        this.audit_time = audit_time;
    }
}
