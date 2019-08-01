package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.ClassInfo;
import com.weiqiaoshiyan.student.manager.mapper.ClassInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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



}
