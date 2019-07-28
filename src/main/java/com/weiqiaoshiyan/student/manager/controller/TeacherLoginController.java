package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.response.Message;
import com.weiqiaoshiyan.student.manager.service.TeacherService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/28.
 */
@RequestMapping("teacher")
@RestController
public class TeacherLoginController {
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("loginIn")
    public Object login(Teacher teacher){
        Map<String,String> loginMessage = (Map<String,String>)teacherService.login(teacher);
        return loginMessage;
    }
    @RequestMapping("register")
    public Object register(@RequestParam Map<String,Object> user){
        boolean register = (boolean)teacherService.register(user);
        if(register){
            return  new Message("success","教师信息注册成功");
        }else{
            return  new Message("failed","教师用户注册失败");
        }
    }

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
}
