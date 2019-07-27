package com.weiqiaoshiyan.student.manager.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Teacher implements Serializable{
    private Integer id;
    private String name;
    private String account;
    private String password;
    private String perms;
}
