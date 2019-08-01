package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.Course;
import com.weiqiaoshiyan.student.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public  Object courseInfo(@RequestParam(name = "pageIndex",defaultValue = "1") Integer pageIndex,
                              @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,@RequestParam Map<String,Object> conditions,Model model) {
        PageHelper.startPage(pageIndex,pageSize);
        List<Course> courses = courseService.selectCourseInfo(conditions);
        PageInfo<Course> coursePageInfo = new PageInfo<>(courses);
        model.addAttribute("courses",coursePageInfo);
        return "teacher/courseInfo";
    }

}
