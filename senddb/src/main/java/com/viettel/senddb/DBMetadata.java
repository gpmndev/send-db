package com.viettel.senddb;

import java.util.Date;

public class DBMetadata {
    private final String fileName;
    private final String appName;
    private final String userId;
    private final String shopId;
    private final String roleId;

    public DBMetadata(String fileName, String appName, String userId, String shopId, String roleId) {
        if (fileName == null || fileName.isEmpty()) {
            this.fileName = String.valueOf(new Date().getTime());
        } else {
            this.fileName = fileName;
        }
        this.appName = appName;
        this.userId = userId;
        this.shopId = shopId;
        this.roleId = roleId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAppName() {
        return appName;
    }

    public String getUserId() {
        return userId;
    }

    public String getShopId() {
        return shopId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String toFilePath() {
        return appName + "/staffs/" + userId + "/" + fileName;
    }
}
