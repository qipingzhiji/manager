package com.weiqiaoshiyan.student.manager.service;

import com.weiqiaoshiyan.student.manager.entity.Course;
import com.weiqiaoshiyan.student.manager.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    public List<Course> selectCourseInfo(Map<String,Object> conditions) {
        return courseMapper.selectCourse(conditions);
    }

    public boolean insertCourse(Course course) {
        return  courseMapper.insertCourse(course) > 0;
    }

    public boolean deleteCourse(Integer[] ids) {
        return   courseMapper.deleteCourse(ids) > 0;
    }

    public boolean updateCourse(Course course) {
        return courseMapper.updateCourse(course) > 0;
    }
}
