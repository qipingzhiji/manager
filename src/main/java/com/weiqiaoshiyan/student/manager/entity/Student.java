package com.weiqiaoshiyan.student.manager.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Student {
    private Integer id;
    private String classNum;
    private  String studentName;
    private Date beginTime;
    private Integer courseId;

}
