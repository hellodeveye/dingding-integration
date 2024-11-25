package com.xiaocm.integration.sync.domain;


public enum SyncType {
    DINGTALK("钉钉"),
    WECHAT_WORK("企业微信"),
    FEISHU("飞书"),
    MANUAL("手动同步");

    private final String description;

    SyncType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static SyncType fromString(String text) {
        for (SyncType b : SyncType.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
