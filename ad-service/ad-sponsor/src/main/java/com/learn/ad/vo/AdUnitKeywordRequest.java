package com.learn.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitKeywordRequest {

    private List<UnitKeyWord> unitKeyWords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public  static class UnitKeyWord{
        private Long unitId;
        private String keyword;
    }
}
