package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/teacher")
    public Object insertTeacher(Teacher teacher) {
        teacher.setName("admin");
        teacher.setAccount("admin");
        teacher.setPassword("admn");
        return teacherService.insertTeacher(teacher);
    }

    @RequestMapping("/deleteTeacher")
    public  Object deleteTeacher(Integer[] ids) {
        ids = new Integer[]{1,2};
        return teacherService.deleteTeacher(ids);
    }

    @RequestMapping("/getTeachers")
    public Object getTeachers(Map<String,Object> conditions) {
        conditions.put("id", 4);
        conditions.put("name", "ad");
        return teacherService.getTeachers(conditions);
    }

    @RequestMapping("/updateTeacher")
    public  Object updateTeacher(Teacher teacher) {
        teacher.setId(3);
        teacher.setName("demo");
        return teacherService.updateTeacher(teacher);
    }

}
