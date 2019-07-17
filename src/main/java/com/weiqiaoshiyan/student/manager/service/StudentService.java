package com.weiqiaoshiyan.student.manager.service;

import com.weiqiaoshiyan.student.manager.entity.Student;
import com.weiqiaoshiyan.student.manager.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public boolean studentSubmit(Student student) {
        int insert = studentMapper.insert(student);
        return insert > 0;
    }

    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    public List<Student> selectByConditon(Map<String,Object> conditons) {
        return studentMapper.selectByCondition(conditons);
    }

    public  boolean delete(Integer[] ids) {
        return  studentMapper.delete(ids) > 0;
    }

    public boolean update(Student student) {
        return  studentMapper.update(student) > 0;
    }
}
