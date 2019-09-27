package com.learn.ad.enitiy;


import com.learn.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ad_user")
public class AdUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name="token",  nullable = false)
    private String token;

    @Column(name="user_status",  nullable = false)
    private Integer userStatus;

    @Column(name="create_time",  nullable = false)
    private Date createTime;

    @Column(name="update_time",  nullable = false)
    private Date updateTime;

    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus= CommonStatus.VALID.getStatus();
        this.createTime=new Date();
        this.updateTime= this.createTime;
    }
}
