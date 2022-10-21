package com.xxx.crm.query;

import com.xxx.crm.base.BaseQuery;
import lombok.Data;

import java.io.Serializable;


@Data
public class AnnouncementQuery extends BaseQuery implements Serializable {

    private String announcement;

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}
