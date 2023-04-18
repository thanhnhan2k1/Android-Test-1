package com.example.test1.model;

import java.io.Serializable;

public class Job implements Serializable {
    private int id;
    private String name, content,finishDate,status;
    private boolean collaborate;

    public Job() {
    }

    public Job(int id, String name, String content, String finishDate, String status, boolean collaborate) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.finishDate = finishDate;
        this.status = status;
        this.collaborate = collaborate;
    }

    public Job(String name, String content, String finishDate, String status, boolean collaborate) {
        this.name = name;
        this.content = content;
        this.finishDate = finishDate;
        this.status = status;
        this.collaborate = collaborate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCollaborate() {
        return collaborate;
    }

    public void setCollaborate(boolean collaborate) {
        this.collaborate = collaborate;
    }
}
