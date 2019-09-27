package com.learn.ad.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CreateUserRequest {
    private String username;
    public boolean validate(){
        log.info(username);
        return !StringUtils.isEmpty(username);
    }
}
