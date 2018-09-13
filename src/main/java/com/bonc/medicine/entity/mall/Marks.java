package com.bonc.medicine.entity.mall;

import java.util.Date;

public class Marks {
	private int id;
	private int supply_id;// 商品id
	private int owner_user_id;// 商品所有者的id。非强制需要
	private int leave_user_id;// 留言者的id
	private String mark_message;// 留言的信息
	private Date leave_time;// 留言的时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(int supply_id) {
		this.supply_id = supply_id;
	}

	public int getOwner_user_id() {
		return owner_user_id;
	}

	public void setOwner_user_id(int owner_user_id) {
		this.owner_user_id = owner_user_id;
	}

	public int getLeave_user_id() {
		return leave_user_id;
	}

	public void setLeave_user_id(int leave_user_id) {
		this.leave_user_id = leave_user_id;
	}

	public String getMark_message() {
		return mark_message;
	}

	public void setMark_message(String mark_message) {
		this.mark_message = mark_message;
	}

	public Date getLeave_time() {
		return leave_time;
	}

	public void setLeave_time(Date leave_time) {
		this.leave_time = leave_time;
	}

}
