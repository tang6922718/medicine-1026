package com.bonc.medicine.entity.mall;

import java.util.Date;

public class Reply {
	private int id;
	private int leave_user_id;// 回复者的id
	private String mark_message_r;// 回复的内容
	private Date mark_time;// 该留言/回复生成的时间
	private String is_read;// 是否已读。1：是，0：否
	private int mark_id;// 留言对象的id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeave_user_id() {
		return leave_user_id;
	}

	public void setLeave_user_id(int leave_user_id) {
		this.leave_user_id = leave_user_id;
	}

	public String getMark_message_r() {
		return mark_message_r;
	}

	public void setMark_message_r(String mark_message_r) {
		this.mark_message_r = mark_message_r;
	}

	public Date getMark_time() {
		return mark_time;
	}

	public void setMark_time(Date mark_time) {
		this.mark_time = mark_time;
	}

	public String getIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public int getMark_id() {
		return mark_id;
	}

	public void setMark_id(int mark_id) {
		this.mark_id = mark_id;
	}

}
