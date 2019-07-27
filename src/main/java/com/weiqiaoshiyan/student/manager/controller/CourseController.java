package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.Course;
import com.weiqiaoshiyan.student.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/courseInfo")
    public List<Course> selectCouserInfo(Map<String,Object> conditions) {
        List<Course> courses = courseService.selectCourseInfo(conditions);
        return courses;
    }

    @RequestMapping("/insertCourse")
    public Object insertCourse(Course course) {
        course.setCourseName("语文");
        course.setTeacherId(3);
        return courseService.insertCourse(course);
    }

    @RequestMapping("/deleteCourse")
    public Object deleteCourse(Integer[] ids) {
        ids = new Integer[]{2,3};
        return  courseService.deleteCourse(ids);
    }

    @RequestMapping("/updateCouserInfo")
    public  Object updateCourse(Course course) {
        course.setId(1);
        course.setCourseName("信息技术与安全");
        return courseService.updateCourse(course);
    }
}
