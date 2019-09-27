package com.learn.ad.constant;

import lombok.Getter;

@Getter
public enum CreativeType {
    IMAGE(1,"图片"),VIDEO(2,"视频"),TEXT(3,"文本");
    private int tpye;
    private String desc;

    CreativeType(int tpye, String desc) {
        this.tpye = tpye;
        this.desc = desc;
    }
}
