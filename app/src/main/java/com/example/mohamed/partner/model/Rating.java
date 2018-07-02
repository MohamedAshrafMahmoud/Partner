package com.example.mohamed.partner.model;

/**
 * Created by mohamed on 4/5/18.
 */

public class Rating {

    private String userName;
    private String projectId;
    private String rateValue;
    private String comment;

    public Rating(String userName, String projectId, String rateValue, String comment) {
        this.userName = userName;
        this.projectId = projectId;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public Rating() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
