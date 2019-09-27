package com.learn.ad.index.adunit;

import com.learn.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdUnitObject {
    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;

    private AdPlanObject adPlanObject;

    void update(AdUnitObject newObject){
        if(newObject.getUnitId()!=null){
            this.unitId=newObject.getUnitId();
        }
        if(newObject.getUnitStatus()!=null){
            this.unitStatus=newObject.getUnitStatus();
        }
        if(newObject.getPositionType()!=null){
            this.positionType=newObject.getPositionType();
        }
        if(newObject.getPlanId()!=null){
            this.planId=newObject.getPlanId();
        }
        if(newObject.getAdPlanObject()!=null){
            this.adPlanObject=newObject.getAdPlanObject();
        }
    }

    private static boolean isKaiping(int positionType){
        return (positionType & AdUnitConstants.POSITION_TYPE.KAIPING)>0;
    }
    private static boolean isTiepian(int positionType){
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN)>0;
    }
    private static boolean isTiepianMiddle(int positionType){
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE)>0;
    }
    private static boolean isTiepianPost(int positionType){
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_POST)>0;
    }
    private static boolean isTiepianPause(int positionType){
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE)>0;
    }

    public static boolean isSlotTypeOk(int adSlotType, int positionType){
        switch (adSlotType){
            case AdUnitConstants.POSITION_TYPE.KAIPING:
                return isKaiping(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN:
                return isTiepian(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTiepianMiddle(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE:
                return isTiepianPause(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return isTiepianPost(positionType);
            default:
                return false;
        }
    }
}
