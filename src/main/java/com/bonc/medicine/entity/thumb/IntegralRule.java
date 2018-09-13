package com.bonc.medicine.entity.thumb;

/**
 * @program: medicine-hn
 * @description: 积分规则实体类
 * @author: hejiajun
 * @create: 2018-09-06 21:37
 **/
public class IntegralRule {

    //id
    private int id;

    //行为编号/类型
    private String actionCode;

    //积分数当前行为的积分数
    private int point;

    //有效标志1：有效 0：无效
    private char isEffect;

    //每日上限
    private int upperBound;

    public IntegralRule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public char getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(char isEffect) {
        this.isEffect = isEffect;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    @Override
    public String toString() {
        return "IntegralRule{" +
                "id=" + id +
                ", actionCode='" + actionCode + '\'' +
                ", point=" + point +
                ", isEffect=" + isEffect +
                ", upperBound=" + upperBound +
                '}';
    }
}
