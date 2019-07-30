package com.weiqiaoshiyan.student.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.weiqiaoshiyan.student.manager.entity.Course;
import com.weiqiaoshiyan.student.manager.entity.Student;
import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.StudentMapper;
import com.weiqiaoshiyan.student.manager.service.CourseService;
import com.weiqiaoshiyan.student.manager.service.StudentInfoService;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sqlite.date.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/23.
 */
@Controller
public class StudentLoginController {

    @Autowired
    private StudentInfoService service;

    @Autowired
    private StudentMapper studentLessonService;

    @Autowired
    private CourseService courseService;

    @Value(value = "${mysharo.hashAlgorithmName}")
    private  String hashAlgorithmName;
    @Value(value = "${mysharo.hashIterations}")
    private int hashIterations;
    @RequestMapping("/student/register")
    public Object studentRegister(StudentInfo studentInfo,Map<String,String> map) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("studentName",studentInfo.getStudentName());
        conditions.put("classId",studentInfo.getClassId());
        conditions.put("login",true);
        if(service.studentRegisterOnly(conditions)){
        if(service.studentRegister(studentInfo)){
            map.put("success","你已经注册成功，请登录来进行签到操作");
        }else{
            map.put("failed","注册失败,请联系老师来进行学生信息的录入");
        }}else  {
            map.put("failed","当前班级已经有叫[ " +  studentInfo.getStudentName() + " ]进行注册了，请尝试在名字后辍下添加数字继续进行注册");
        };
        return "login";
    }

    @RequestMapping("/student/student_sign")
    public Object studentSignInfo(@ModelAttribute("loginStudent") String loginStudent,@ModelAttribute("updateStatus") String updateStatus, Map<String,Object> res) {
        StudentInfo studentInfo = JSONObject.parseObject(loginStudent, StudentInfo.class);
        res.put("loginStudent", studentInfo);
        res.put("updateStatus",updateStatus);
        return "student_sign";
    }

    @RequestMapping("/student/login")
    public Object studentLogin(@RequestParam Map<String, Object> conditions, @ModelAttribute("loginStudent") String updateLoginStudentInfo,@ModelAttribute("updateStatus") String updateStatus, RedirectAttributes redirectAttributes, Model model) {


        String json ="";
        if(StringUtils.isEmpty(updateLoginStudentInfo)){
            Map<String, String> errorMap = service.studentLogin(conditions);
            if (errorMap.get("errorPage") != null) {
                model.addAllAttributes(errorMap);
                return errorMap.get("errorPage");
            }
            StudentInfo studentInfo = (StudentInfo) SecurityUtils.getSubject().getPrincipal();
            studentInfo.setPassword(null);
            studentInfo.setSalt(null);
            json = JSONObject.toJSONString(studentInfo);
        }else{
            json =updateLoginStudentInfo;
            redirectAttributes.addAttribute("updateStatus",updateStatus);
        }

        redirectAttributes.addAttribute("loginStudent",json );

        return "redirect:/student/student_sign";
    }

    @RequestMapping("/student/signed")
    @ResponseBody
    public Object signed( Student student){
        Map<String,Object> message = new HashMap<>();
        Date now = new Date();
        String format = DateFormatUtils.format(now, "yyyy-MM-dd");
        student.setBeginTime(now);
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("studentId",student.getStudentId());
        conditions.put("courseId",student.getCourseId());
        List<Student> students = studentLessonService.selectByCondition(conditions);
        if(students.size() > 0) {
            conditions.clear();
            conditions.put("id",student.getCourseId());
            conditions.put("teacherId",student.getTeacherId());
            List<Course> courses = courseService.selectCourseInfo(conditions);
            Date beginTime = students.get(0).getBeginTime();
            Integer courseTime = courses.get(0).getCourseTime();
            if((now.getTime()-beginTime.getTime())/1000/60  >courseTime){
                if(studentLessonService.insert(student) > 0){

                    message.put("success","签到成功");
                    return  true;
                }else{
                    message.put("failed","签到失败");
                    return  false;
                }
            } else {
                message.put("failed","在授课时间[" + courseTime+"]内已经签过到，不用继续签到");
                return  false;
            }
        } else{
            return studentLessonService.insert(student) > 0;
        }
    }

    @RequestMapping("/student/updateInfo")
    public Object updateStudentInfo(StudentInfo studentInfo ,RedirectAttributes redirectAttributes,Model model){
        if("".equals(studentInfo.getPassword())){
            studentInfo.setPassword(null);
        } else{
            StudentInfo studentInfoById = service.getStudentInfoById(studentInfo.getId());
            String salt = studentInfoById.getSalt();
            Object password = new SimpleHash(hashAlgorithmName, studentInfo.getPassword(), salt,hashIterations);
            studentInfo.setPassword(password.toString());
        }
        StudentInfo studentInfoOne = null;
        if(service.updateStudentInfo(studentInfo)){
            studentInfoOne = service.getStudentInfoById(studentInfo.getId());
            //更新shiro认证的信息
            Subject subject = SecurityUtils.getSubject();
            PrincipalCollection previousPrincipals = subject.getPreviousPrincipals();
            String realmName = "";
            if(previousPrincipals !=null){
                realmName = previousPrincipals.getRealmNames().iterator().next();
            }
             realmName = subject.getPrincipals().getRealmNames().iterator().next();
            SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection(studentInfoOne,realmName);
            subject.runAs(simplePrincipalCollection);
            //重定向时发送参数
            redirectAttributes.addAttribute("updateStatus","学生信息更新成功");
        }else{
            redirectAttributes.addAttribute("updateStatus","学生信息更新失败");
        }
        if(!StringUtils.isEmpty(studentInfo.getPassword())) {
            model.addAttribute("updateStatus","你的密码已经更改，请重新登录");
            return  "login";
        }
        Object principal = SecurityUtils.getSubject().getPrincipal();
        studentInfoOne.setSalt(null);
        studentInfoOne.setPassword(null);
        String json = JSONObject.toJSONString(studentInfoOne);
        redirectAttributes.addAttribute("loginStudent",json );

        return "redirect:/student/login";
    }

}
