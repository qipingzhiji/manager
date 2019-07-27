package com.weiqiaoshiyan.student.manager.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable{
    private Integer id;
    private String courseName;
    private Integer teacherId;
    private Integer courseTime;
    private String teacherName;
    private Teacher teacher;
}
