package com.learn.ad.index;

import lombok.Getter;

@Getter
public enum  CommonStatus {
    VALID(1, "有效"),INVALID(0,"无效");
    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
