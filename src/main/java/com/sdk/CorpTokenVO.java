package com.sdk;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业授权套件TOKEN的VO
 */
public class CorpTokenVO implements Serializable{
    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 企业授权套件token
     */
    private String corpToken;

    /**
     * 过期时间
     */
    private Date expiredTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCorpToken() {
        return corpToken;
    }

    public void setCorpToken(String corpToken) {
        this.corpToken = corpToken;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}
