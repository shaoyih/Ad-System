package com.learn.ad.dto;

import com.learn.ad.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {
    private String tableName;
    private String level;
    private Map<OpType, List<String>> opTypeFieldSetMap= new HashMap<>();

    private Map<Integer, String> posMap= new HashMap<>();
}
