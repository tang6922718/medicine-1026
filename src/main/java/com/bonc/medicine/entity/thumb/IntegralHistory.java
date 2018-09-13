package com.bonc.medicine.entity.thumb;

/**
 * @program: medicine-hn
 * @description: 积分历史记录实体类
 * @author: hejiajun
 * @create: 2018-09-07 20:41
 **/
public class IntegralHistory {

    private int userId;

    private int actionCode;

    private int point;

    private String actionTime;

    public IntegralHistory() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    @Override
    public String toString() {
        return "IntegralHistory{" +
                "userId=" + userId +
                ", actionCode=" + actionCode +
                ", point=" + point +
                ", actionTime='" + actionTime + '\'' +
                '}';
    }
}
