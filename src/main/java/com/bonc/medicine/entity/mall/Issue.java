package com.bonc.medicine.entity.mall;

import java.util.Date;

public class Issue {
	private String id; // 编号
	private String issue_desc; // 问题描述
	private String img_url; // 图片url
	private String video_url; // 视频url
	private char is_public; // 是否为案例（公开）
	private Date issue_time; // 提问时间
	private String issue_address; // 提问地址
	private int issue_user_id; // 提问人id
	private char issue_status; // 问题状态0未处理1进行中2已结束3已关闭
	private int score; // 评分
	private int follow_days; // 跟踪天数
	private char is_revisited; // 是否回访0否1是
	private String revisited_mark; // 回访结论
	private int cat_code; // 涉及品类id
	private String field_ids; // 关联地块（逗号连接的字符串）
	
	
	public String getField_ids() {
		return field_ids;
	}
	public void setField_ids(String field_ids) {
		this.field_ids = field_ids;
	}
	public int getCat_code() {
		return cat_code;
	}
	public void setCat_code(int cat_code) {
		this.cat_code = cat_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssue_desc() {
		return issue_desc;
	}
	public void setIssue_desc(String issue_desc) {
		this.issue_desc = issue_desc;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public char getIs_public() {
		return is_public;
	}
	public void setIs_public(char is_public) {
		this.is_public = is_public;
	}
	public Date getIssue_time() {
		return issue_time;
	}
	public void setIssue_time(Date issue_time) {
		this.issue_time = issue_time;
	}
	public String getIssue_address() {
		return issue_address;
	}
	public void setIssue_address(String issue_address) {
		this.issue_address = issue_address;
	}
	public int getIssue_user_id() {
		return issue_user_id;
	}
	public void setIssue_user_id(int issue_user_id) {
		this.issue_user_id = issue_user_id;
	}
	public char getIssue_status() {
		return issue_status;
	}
	public void setIssue_status(char issue_status) {
		this.issue_status = issue_status;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getFollow_days() {
		return follow_days;
	}
	public void setFollow_days(int follow_days) {
		this.follow_days = follow_days;
	}
	public char getIs_revisited() {
		return is_revisited;
	}
	public void setIs_revisited(char is_revisited) {
		this.is_revisited = is_revisited;
	}
	public String getRevisited_mark() {
		return revisited_mark;
	}
	public void setRevisited_mark(String revisited_mark) {
		this.revisited_mark = revisited_mark;
	}
	
	
}
