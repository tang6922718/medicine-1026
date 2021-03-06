package com.bonc.medicine.entity.field;

public class Notice {
	private int id;
	private String notice_type;
	private int object_id;
	private String notice_content;
	private int publish_user_id;
	private int notice_role_type;
	private String notice_receiver;
	private String img_url;
	private String publish_time;
	private String status;
	private String state;
	private String send_time;
	private String is_send;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNotice_type() {
		return notice_type;
	}

	public void setNotice_type(String notice_type) {
		this.notice_type = notice_type;
	}

	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public int getPublish_user_id() {
		return publish_user_id;
	}

	public void setPublish_user_id(int publish_user_id) {
		this.publish_user_id = publish_user_id;
	}

	public int getNotice_role_type() {
		return notice_role_type;
	}

	public void setNotice_role_type(int notice_role_type) {
		this.notice_role_type = notice_role_type;
	}

	public String getNotice_receiver() {
		return notice_receiver;
	}

	public void setNotice_receiver(String notice_receiver) {
		this.notice_receiver = notice_receiver;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	public String getIs_send() {
		return is_send;
	}

	public void setIs_send(String is_send) {
		this.is_send = is_send;
	}
	
}
