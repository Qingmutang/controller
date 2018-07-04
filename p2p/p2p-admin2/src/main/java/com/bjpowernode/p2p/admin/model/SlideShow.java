package com.bjpowernode.p2p.admin.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图信息对象
 * 
 * @author yanglijun
 *
 */
public class SlideShow implements Serializable {
	
	private static final long serialVersionUID = -3721308772381601471L;

	private Integer id;

    private String slideTitle;

    private String slideUrl;

    private String slideImageUrl;

    private Date slideStartTime;

    private Date slideEndTime;

    private Integer slideStatus;

    private Date slideCreateTime;

    private Date slideUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlideTitle() {
        return slideTitle;
    }

    public void setSlideTitle(String slideTitle) {
        this.slideTitle = slideTitle;
    }

    public String getSlideUrl() {
        return slideUrl;
    }

    public void setSlideUrl(String slideUrl) {
        this.slideUrl = slideUrl;
    }

    public String getSlideImageUrl() {
        return slideImageUrl;
    }

    public void setSlideImageUrl(String slideImageUrl) {
        this.slideImageUrl = slideImageUrl;
    }

    public Date getSlideStartTime() {
        return slideStartTime;
    }

    public void setSlideStartTime(Date slideStartTime) {
        this.slideStartTime = slideStartTime;
    }

    public Date getSlideEndTime() {
        return slideEndTime;
    }

    public void setSlideEndTime(Date slideEndTime) {
        this.slideEndTime = slideEndTime;
    }

    public Integer getSlideStatus() {
        return slideStatus;
    }

    public void setSlideStatus(Integer slideStatus) {
        this.slideStatus = slideStatus;
    }

    public Date getSlideCreateTime() {
        return slideCreateTime;
    }

    public void setSlideCreateTime(Date slideCreateTime) {
        this.slideCreateTime = slideCreateTime;
    }

    public Date getSlideUpdateTime() {
        return slideUpdateTime;
    }

    public void setSlideUpdateTime(Date slideUpdateTime) {
        this.slideUpdateTime = slideUpdateTime;
    }
}