package com.bonc.medicine.entity.knowledgebase;
/**
 * @Description :  sop步骤
 * @Date :  2018.8.29
 */

public class SopStep {
    private int id;//编号
    private int sop_id;//品种SOP编号
    private String step_name;//步骤名称
    private String short_desc;//简单说明
    private String detail_desc;//详细说明
    private String next_suggestion;//下一环节建议
    private String recommendation;//推荐农机
    private int step_order;//顺序号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSop_id() {
        return sop_id;
    }

    public void setSop_id(int sop_id) {
        this.sop_id = sop_id;
    }

    public String getStep_name() {
        return step_name;
    }

    public void setStep_name(String step_name) {
        this.step_name = step_name;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getDetail_desc() {
        return detail_desc;
    }

    public void setDetail_desc(String detail_desc) {
        this.detail_desc = detail_desc;
    }

    public String getNext_suggestion() {
        return next_suggestion;
    }

    public void setNext_suggestion(String next_suggestion) {
        this.next_suggestion = next_suggestion;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public int getStep_order() {
        return step_order;
    }

    public void setStep_order(int step_order) {
        this.step_order = step_order;
    }
}
