package com.bonc.medicine.entity.mall;

import java.util.Date;

public class Specialist {
	private int spec_id; // 专家编号
	private String name; // 姓名
	private String head_portrait; // 头像
	private String education; // 学历
	private String title; // 职称
	private String company; // 单位
	private String professional_direction; // 专业方向
	private String expertise_field; // 擅长
	private int employment_age; // 从业时间
	private String[] cat_code; // 品类编码
	private String[] subject_code; // 学科编码
	private Date registration_date; // 注册日期
	private String detail; // 详细介绍 
	
	public String[] getCat_code() {
		return cat_code;
	}
	public String[] getSubject_code() {
		return subject_code;
	}
	public Date getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
	public void setCat_code(String[] cat_code) {
		this.cat_code = cat_code;
	}
	public void setSubject_code(String[] subject_code) {
		this.subject_code = subject_code;
	}
	public int getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(int spec_id) {
		this.spec_id = spec_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead_portrait() {
		return head_portrait;
	}
	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getProfessional_direction() {
		return professional_direction;
	}
	public void setProfessional_direction(String professional_direction) {
		this.professional_direction = professional_direction;
	}
	public String getExpertise_field() {
		return expertise_field;
	}
	public void setExpertise_field(String expertise_field) {
		this.expertise_field = expertise_field;
	}
	public int getEmployment_age() {
		return employment_age;
	}
	public void setEmployment_age(int employment_age) {
		this.employment_age = employment_age;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
