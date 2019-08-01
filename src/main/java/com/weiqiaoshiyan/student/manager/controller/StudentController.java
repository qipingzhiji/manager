package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.Student;
import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.StudentInfoMapper;
import com.weiqiaoshiyan.student.manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @ResponseBody
    @RequestMapping("/allStudentInfo")
    public Object listAllStudentInfo() {
        return  studentService.selectAll();
    }
    @ResponseBody
    @RequestMapping("/listInfoByConditions")
    public Object listStudentInfoByConditions(@RequestParam Map<String, Object> conditions) {
        List<Student> students = studentService.selectByConditon(conditions);
        return students;
    }

    @GetMapping("studentPage")
    public Object studentPage(){
        return "teacher/studentsInfo";
    }



    @ResponseBody
    @GetMapping("students")
    public Object students(@RequestParam Integer rows,@RequestParam Integer page,@RequestParam String sortOrder) {
        List<StudentInfo> students = new ArrayList<>();
        PageHelper.startPage(page,rows);
        students = studentInfoMapper.listByConditon(new HashMap<String,Object>());
        PageInfo<StudentInfo> studentPageInfo = new PageInfo<>(students);
        return studentPageInfo;
    }

}
