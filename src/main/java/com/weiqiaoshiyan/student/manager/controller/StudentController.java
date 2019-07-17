package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.Student;
import com.weiqiaoshiyan.student.manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/submit")
    public Object studentInfoSubmit() {
        Student student = new Student();
        student.setBeginTime(new Date());
        student.setClassNum("0613");
        student.setCourseId(1);
        student.setStudentName("admin");
        return studentService.studentSubmit(student);
    }

    @RequestMapping("/allStudentInfo")
    public Object listAllStudentInfo() {
        return  studentService.selectAll();
    }

    @RequestMapping("/listInfoByConditions")
    public Object listStudentInfoByConditions(Map<String, Object> conditions) {
        conditions.put("studentName", "ad");
        return  studentService.selectByConditon(conditions);
    }



    @RequestMapping("/deleteStudentInfoById")
    public Object delete(Integer[]  ids) {
        return studentService.delete(new Integer[]{1,2,3,4,5});
    }

    @RequestMapping("/updateStudentInfo")
    public Object upateStudent(Student student) {
        student.setId(12);
        student.setStudentName("test");
        return studentService.update(student);
    }

}
