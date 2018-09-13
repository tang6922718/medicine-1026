package com.bonc.medicine.entity.information;

import javax.xml.crypto.Data;

public class InfoBasic {
    int id;
    String title;
    int source_code;
    int cat_code;
    String content;
    String fetch_date;
    String publish_date;
    String status;
    String ismarket;
    String istopline;
    String isalarm;
    String newspic;
    int click_count;
    int read_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSource_code() {
        return source_code;
    }

    public void setSource_code(int source_code) {
        this.source_code = source_code;
    }

    public int getCat_code() {
        return cat_code;
    }

    public void setCat_code(int cat_code) {
        this.cat_code = cat_code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFetch_date() {
        return fetch_date;
    }

    public void setFetch_date(String fetch_date) {
        this.fetch_date = fetch_date;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsmarket() {
        return ismarket;
    }

    public void setIsmarket(String ismarket) {
        this.ismarket = ismarket;
    }

    public String getIstopline() {
        return istopline;
    }

    public void setIstopline(String istopline) {
        this.istopline = istopline;
    }

    public String getIsalarm() {
        return isalarm;
    }

    public void setIsalarm(String isalarm) {
        this.isalarm = isalarm;
    }

    public String getNewspic() {
        return newspic;
    }

    public void setNewspic(String newspic) {
        this.newspic = newspic;
    }

    public int getClick_count() {
        return click_count;
    }

    public void setClick_count(int click_count) {
        this.click_count = click_count;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public Data getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Data update_date) {
        this.update_date = update_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    Data update_date;
    int user_id;
    String origin_title;


}
