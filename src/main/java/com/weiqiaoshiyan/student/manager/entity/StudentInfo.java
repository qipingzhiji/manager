package com.weiqiaoshiyan.student.manager.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Data
public class StudentInfo implements Serializable{
    private Integer id;
    private String studentNumber;
    private String studentName;
    private Integer sex;
    private String password;
    private Integer classId;
    private Date createTime;
    private ClassInfo classInfo;
    private Course course;
}
