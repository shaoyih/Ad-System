package com.learn.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {
    //应用编码
    private String appCode;
    private String appName;
    private String packageName;
    private String activityName;
}
