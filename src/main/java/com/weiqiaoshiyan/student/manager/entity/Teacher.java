package com.weiqiaoshiyan.student.manager.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@Data
public class Teacher implements Serializable{

    private Integer id;
    @JSONField(name = "teacherName")
    private String name;
    @JSONField(name = "teacherAccount")
    private String account;
    @JSONField(name = "teacherPassword")
    private String password;
    private String perms;
    private String salt;
}
