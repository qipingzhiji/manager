package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.Course;
import com.weiqiaoshiyan.student.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ResponseBody
    @RequestMapping("/courseInfo")
    public List<Course> selectCouserInfo(Map<String,Object> conditions) {
        List<Course> courses = courseService.selectCourseInfo(conditions);
        return courses;
    }

    @GetMapping("courses")
    public  Object courseInfo(Model model) {
        List<Course> courses = courseService.selectCourseInfo(new HashMap<String, Object>());
        model.addAttribute("courses",courses);
        return "teacher/courseInfo";
    }

}
