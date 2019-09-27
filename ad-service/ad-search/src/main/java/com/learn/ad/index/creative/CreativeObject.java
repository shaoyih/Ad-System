package com.learn.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {
    private Long adId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String adUrl;

    public void update(CreativeObject newObject){
        if(newObject.getAdId()!=null)
            this.adId=newObject.getAdId();
        if(newObject.getName()!=null)
            this.name=newObject.getName();
        if(newObject.getType()!=null)
            this.type=newObject.getType();
        if(newObject.getMaterialType()!=null)
            this.materialType=newObject.getMaterialType();
        if(newObject.getHeight()!=null)
            this.height=newObject.getHeight();
        if(newObject.getWidth()!=null)
            this.width=newObject.getWidth();
        if(newObject.getAuditStatus()!=null)
            this.auditStatus=newObject.getAuditStatus();
        if(newObject.getAdUrl()!=null)
            this.adUrl=newObject.getAdUrl();
    }
}
