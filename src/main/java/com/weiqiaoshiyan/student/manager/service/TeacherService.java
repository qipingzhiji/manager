package com.weiqiaoshiyan.student.manager.service;

import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public boolean insertTeacher(Teacher teacher) {
        return  teacherMapper.insertTeacher(teacher) > 0;
    }

    public boolean deleteTeacher(Integer[] ids) {
        return teacherMapper.deleteTeacher(ids)>0;
    }

    public List<Teacher> getTeachers(Map<String,Object> conditions) {
        return teacherMapper.selectTeachers(conditions);
    }

    public boolean updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher) > 0;
    }
}
