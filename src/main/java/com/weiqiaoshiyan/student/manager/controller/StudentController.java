package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.Student;
import com.weiqiaoshiyan.student.manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/allStudentInfo")
    public Object listAllStudentInfo() {
        return  studentService.selectAll();
    }

    @RequestMapping("/listInfoByConditions")
    public Object listStudentInfoByConditions(@RequestParam Map<String, Object> conditions) {
        List<Student> students = studentService.selectByConditon(conditions);
        return students;
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
