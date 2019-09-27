package com.learn.ad.constant;


import com.github.shyiko.mysql.binlog.event.EventType;

public enum OpType {
    ADD,UPDATE,DELETE,OTHER;

    public static OpType to(EventType eventType){
        switch(eventType){
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
