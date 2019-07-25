package com.weiqiaoshiyan.student.manager.service;

import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.StudentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Service
public class StudentInfoService {
    @Autowired
    private StudentInfoMapper dao;

    public boolean studentRegister(StudentInfo studentInfo) {
        studentInfo.setCreateTime(new Date());
        return dao.insert(studentInfo) > 0;
    }
}
