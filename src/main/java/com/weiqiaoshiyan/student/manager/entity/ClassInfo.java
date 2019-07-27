package com.weiqiaoshiyan.student.manager.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Data
public class ClassInfo  implements Serializable{
    private Integer id;
    private String className;
    private String classComment;
}
