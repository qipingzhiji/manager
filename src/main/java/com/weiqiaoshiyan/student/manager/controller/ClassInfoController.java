package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.ClassInfo;
import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.ClassInfoMapper;
import com.weiqiaoshiyan.student.manager.response.Message;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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

    @ResponseBody
    @PutMapping("/classInfo")
    public Object updateClassInfo(@RequestBody ClassInfo classInfo){
        if(dao.update(classInfo)>0){
            return new Message("success","更新班级信息成功");
        }else {
            return new Message("failed","更新班级信息失败");
        }
    }

    @ResponseBody
    @PostMapping("/classInfo")
    public Object addClassInfo(@RequestBody ClassInfo classInfo){
        if(dao.insert(classInfo)>0){
            return new Message("success","添加班级信息成功");
        }else {
            return new Message("failed","添加班级信息失败");
        }
    }

    @ResponseBody
    @DeleteMapping("/classInfo")
    public Object deleteClassInfo(@RequestBody Integer[] ids){
        if(dao.delete(ids)>0){
            return new Message("success","删除班级信息成功");
        }else {
            return new Message("failed","删除班级信息失败");
        }
    }


    @GetMapping("classInfo")
    public Object classInfo(Model model) {
        List<ClassInfo> classInfos = dao.selectClassInfoByConditions(new HashMap<String, Object>());
        model.addAttribute("classInfos",classInfos);
        return "teacher/classInfo";
    }

    @ResponseBody
    @GetMapping("classInfos")
    public Object students(@RequestParam Integer rows,@RequestParam Integer page,@RequestParam String sortOrder,@RequestParam String className) {
        List<ClassInfo> classInfos = new ArrayList<>();
        PageHelper.startPage(page,rows);
        val stringObjectHashMap = new HashMap<String, Object>();
        if(!StringUtils.isEmpty(className)){
            stringObjectHashMap.put("className",className);
        }
        classInfos = dao.selectClassInfoByConditions(stringObjectHashMap);
        PageInfo<ClassInfo> classInfoPageInfo = new PageInfo<>(classInfos);
        return classInfoPageInfo;
    }

}
