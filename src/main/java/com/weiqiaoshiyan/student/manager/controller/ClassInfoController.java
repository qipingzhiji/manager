package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.ClassInfo;
import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.ClassInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Controller
public class ClassInfoController {
    @Autowired
    private ClassInfoMapper dao;

    @ResponseBody
    @RequestMapping("/ClassInfos")
    public Object classInfoByConditions(@RequestBody(required = true) Map<String,Object> conditions){
        return dao.selectClassInfoByConditions(conditions);
    }


    @GetMapping("classInfo")
    public Object classInfo(Model model) {
        List<ClassInfo> classInfos = dao.selectClassInfoByConditions(new HashMap<String, Object>());
        model.addAttribute("classInfos",classInfos);
        return "teacher/classInfo";
    }

    @ResponseBody
    @GetMapping("classInfos")
    public Object students(@RequestParam Integer rows,@RequestParam Integer page,@RequestParam String sortOrder) {
        List<ClassInfo> classInfos = new ArrayList<>();
        PageHelper.startPage(page,rows);
        classInfos = dao.selectClassInfoByConditions(new HashMap<String,Object>());
        PageInfo<ClassInfo> classInfoPageInfo = new PageInfo<>(classInfos);
        return classInfoPageInfo;
    }

}
