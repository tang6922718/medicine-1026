package com.bonc.medicine.entity.user;

public class Expert {

	private int spec_id;
	private int employment_age;
	private String education;
	private String expertise_field;
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

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
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
