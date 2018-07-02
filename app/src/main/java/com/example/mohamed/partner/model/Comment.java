package com.example.mohamed.partner.model;

public class Comment {

    private String userName;
    private String projectId;
    private String comment;

    public Comment(String userName, String projectId, String comment) {
        this.userName = userName;
        this.projectId = projectId;
        this.comment = comment;
    }

    public Comment() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
