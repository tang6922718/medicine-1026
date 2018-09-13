package com.bonc.medicine.entity.knowledgebase;

/**
 * 知识库-药典信息
 * Created by Administrator on 2018/8/29.
 */
public class PharmacopoeiaInfo {
    private int id;// 编号
    private int cat_code;// 品种编号
    private String atlas;// 药典状态(1.已导入,2.未导入)
    private String variety_desc;// 品种描述
    private String feature;// 形态特征
    private String difference;// 鉴别
    private String other_aspect;// 其他等

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_code() {
        return cat_code;
    }

    public void setCat_code(int cat_code) {
        this.cat_code = cat_code;
    }

    public String getAtlas() {
        return atlas;
    }

    public void setAtlas(String atlas) {
        this.atlas = atlas;
    }

    public String getVariety_desc() {
        return variety_desc;
    }

    public void setVariety_desc(String variety_desc) {
        this.variety_desc = variety_desc;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public String getOther_aspect() {
        return other_aspect;
    }

    public void setOther_aspect(String other_aspect) {
        this.other_aspect = other_aspect;
    }
}
