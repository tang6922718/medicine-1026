package com.bonc.medicine.entity.user;

public class Expert {

	private int spec_id;
	private int employment_age;//从业时间
	private String name;//学历
	private String education;//学历
	private String professional_direction;//专业方向
	private String title;//职称
	private String company;//公司
	private String expertise_field;//擅长
	private String cat_rel;// 逗号分隔
	private String subject_rel;// 逗号分隔
	private String detail;
	public int getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(int spec_id) {
		this.spec_id = spec_id;
	}
	public int getEmployment_age() {
		return employment_age;
	}
	public void setEmployment_age(int employment_age) {
		this.employment_age = employment_age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getProfessional_direction() {
		return professional_direction;
	}
	public void setProfessional_direction(String professional_direction) {
		this.professional_direction = professional_direction;
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
	public String getExpertise_field() {
		return expertise_field;
	}
	public void setExpertise_field(String expertise_field) {
		this.expertise_field = expertise_field;
	}
	public String getCat_rel() {
		return cat_rel;
	}
	public void setCat_rel(String cat_rel) {
		this.cat_rel = cat_rel;
	}
	public String getSubject_rel() {
		return subject_rel;
	}
	public void setSubject_rel(String subject_rel) {
		this.subject_rel = subject_rel;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}
