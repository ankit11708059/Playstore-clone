package com.example.cloneplaystore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Permission {

    @SerializedName("permission")
    @Expose
    private String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("type")
    @Expose
    private String type;
}
