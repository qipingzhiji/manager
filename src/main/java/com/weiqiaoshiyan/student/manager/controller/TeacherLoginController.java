package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.response.Message;
import com.weiqiaoshiyan.student.manager.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/28.
 */
@RequestMapping("teacher")
@Controller
public class TeacherLoginController {
    @Autowired
    private TeacherService teacherService;

    @ResponseBody
    @RequestMapping("loginIn")
    public Object login(@RequestBody Teacher teacher){
        Map<String,String> loginMessage = (Map<String,String>)teacherService.login(teacher);
        return loginMessage;
    }
    @ResponseBody
    @RequestMapping("register")
    public Object register(@RequestParam Map<String,Object> user){
        boolean register = (boolean)teacherService.register(user);
        if(register){
            return  new Message("success","教师信息注册成功");
        }else{
            return  new Message("failed","教师用户注册失败");
        }
    }

    @ResponseBody
    @RequestMapping("isOnlyAccount")
    public Object isOnlyAccount(@RequestParam Map<String,Object> user){
        Boolean flag= (Boolean)teacherService.isOnlyAccount(user);
        user.clear();
        if(flag){
            user.put("valid",flag);
            return  user;
        }else {
            user.put("valid",flag);
            return user;
        }
    }

    @RequestMapping("/teacherManager/{id}")
    public String teacherManage(@PathVariable(value = "id")  String id) {
        return "teacher/teacher_manager";
    }
}
