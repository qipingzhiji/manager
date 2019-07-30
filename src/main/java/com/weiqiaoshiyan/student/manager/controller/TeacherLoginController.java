package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.Student;
import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.mapper.StudentMapper;
import com.weiqiaoshiyan.student.manager.response.Message;
import com.weiqiaoshiyan.student.manager.service.StudentService;
import com.weiqiaoshiyan.student.manager.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/28.
 */
@RequestMapping("teacher")
@Controller
public class TeacherLoginController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentMapper studentLessonService;

    @RequestMapping("loginIn")
    public Object login(Teacher teacher, HttpServletRequest request){
        Map<String,String> loginMessage = (Map<String,String>)teacherService.login(teacher);
        if(!StringUtils.isEmpty(loginMessage.get("error"))){
            request.setAttribute("error",loginMessage.get("error"));
            return "teacher/teacher_login";
        }
        //mv.addObject("id",loginMessage.get("id"));
        //mv.addObject("loginUser",loginMessage.get("loginUser"));
        request.getSession().setAttribute("loginUser",(Teacher)SecurityUtils.getSubject().getPrincipal());
        return "redirect:teacherManager/"+ String.valueOf(loginMessage.get("id"));
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
    public String teacherManage(@PathVariable(value = "id")  String id, Model model) {
        Teacher teacher= (Teacher)SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("loginUser",teacher.getName());
        model.addAttribute("id",id);
        return "teacher/teacher_manager";
    }

    @ResponseBody
    @RequestMapping("listSignedStudentInfo")
    public Object studentSignedInfoByTeacherId(@RequestParam Map<String,Object> selectCondition) {
        List<Student> students = new ArrayList<>();
        if(selectCondition != null) {
            PageHelper.startPage(Integer.valueOf( (String) selectCondition.get("page")),Integer.valueOf((String)selectCondition.get("limit")));
            Map<String,Object> conditions = new HashMap<>();
            conditions.put("teacherId",selectCondition.get("teacherId"));
            students = studentLessonService.selectByCondition(conditions);
        }
        PageInfo<Student> studentPageInfo = new PageInfo<>(students);
        return studentPageInfo;
    }
}
