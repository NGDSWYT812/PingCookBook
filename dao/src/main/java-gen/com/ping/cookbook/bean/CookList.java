package com.ping.cookbook.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "COOK_LIST".
 */
public class CookList implements java.io.Serializable {

    private Long id;
    private Long saveTime;
    private String type;
    private Long tid;

    public CookList() {
    }

    public CookList(Long id) {
        this.id = id;
    }

    public CookList(Long id, Long saveTime, String type, Long tid) {
        this.id = id;
        this.saveTime = saveTime;
        this.type = type;
        this.tid = tid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

}
