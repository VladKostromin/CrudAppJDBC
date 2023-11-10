package com.crudapp.enums;

public enum PostStatus {
    ACTIVE("ACTIVE"), UNDER_REVIEW("UNDER REVIEW"), DELETED("DELETED");
    private String desc;

    PostStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
