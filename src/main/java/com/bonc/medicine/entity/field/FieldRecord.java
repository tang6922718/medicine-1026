package com.bonc.medicine.entity.field;

import java.util.Date;

/**
 * @ClassName FieldRecord
 * @Description 农事操作记录实体类
 * @Author YQ
 * @Date 2018/9/1 16:48
 * @Version 1.0
 */
public class FieldRecord {
    private int id; //编号
    private int field_id; //田块编号
    private int segment_id; // 农事操作(SOP步骤编号)
    private String photo_url; // 拍照照片url
    private String video_url; // 视频url
    private Date record_time; // 记录时间
    private String guide_flag; // 技术指导标识   0 是     1  不是
    private int record_user_id;  // 记录用户编号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public int getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(int segment_id) {
        this.segment_id = segment_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public String getGuide_flag() {
        return guide_flag;
    }

    public void setGuide_flag(String guide_flag) {
        this.guide_flag = guide_flag;
    }

    public int getRecord_user_id() {
        return record_user_id;
    }

    public void setRecord_user_id(int record_user_id) {
        this.record_user_id = record_user_id;
    }
}
