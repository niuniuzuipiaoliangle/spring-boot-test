package com.servyou.test.demo.test.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 聪聪只爱笨笨
 */
@Data
public class User {
    private String id;
    private String username;
    private String password;
    private Date createTime;
}
