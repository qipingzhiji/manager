package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.Student;
import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.ClassInfoMapper;
import com.weiqiaoshiyan.student.manager.mapper.StudentInfoMapper;
import com.weiqiaoshiyan.student.manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentInfoMapper studentInfoMapper;
    @Autowired
    private ClassInfoMapper classInfoDao;

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
    public Object studentPage(Model model){
        model.addAttribute("classInfos",classInfoDao.selectClassInfoByConditions(new HashMap<String,Object>()));
        return "teacher/studentsInfo";
    }



    @ResponseBody
    @GetMapping("students")
    public Object students(@RequestParam Integer rows, @RequestParam Integer page, @RequestParam String sortOrder,@RequestParam(required = false) String studentName,@RequestParam(required = false) String classId) {
        List<StudentInfo> students = new ArrayList<>();
        PageHelper.startPage(page,rows);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        if(!"点击此处展开选项来选择班级".equals(classId)){
            stringObjectHashMap.put("classId",classId);
        }
        stringObjectHashMap.put("studentName",studentName);
        students = studentInfoMapper.listByConditon(stringObjectHashMap);
        PageInfo<StudentInfo> studentPageInfo = new PageInfo<>(students);
        return studentPageInfo;
    }


}
