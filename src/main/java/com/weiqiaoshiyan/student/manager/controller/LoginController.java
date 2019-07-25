package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhang_htao on 2019/7/23.
 */
@Controller
public class LoginController {

    @Autowired
    private StudentInfoService service;

    @RequestMapping("/student/register")
    public Object studentRegister(StudentInfo studentInfo) {
        return (service.studentRegister(studentInfo))?"success":"login";
    }

}
