package com.weiqiaoshiyan.student.manager.service;

import com.alibaba.fastjson.JSONObject;
import com.weiqiaoshiyan.student.manager.config.shiro.StudentAndTeacherUsernamePasswordToken;
import com.weiqiaoshiyan.student.manager.config.shiro.UserModularRealmAuthenticator;
import com.weiqiaoshiyan.student.manager.constant.LoginType;
import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.mapper.TeacherMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    @Value("${mysharo.hashIterations}")
    private int hashIterations;
    @Value("${mysharo.hashAlgorithmName}")
    private String hashAlgorithmName;
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



    public Object login(Teacher teacher) {
        Map<String,Object> message = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        StudentAndTeacherUsernamePasswordToken userModularRealmAuthenticator = new StudentAndTeacherUsernamePasswordToken(
                teacher.getAccount(),
                teacher.getPassword(),
                LoginType.TEACHER.getLogin()
        );

        try {
            subject.login(userModularRealmAuthenticator);
        } catch (UnknownAccountException e) {
            message.put("error","没有该帐户");
            return  message;
        } catch (IncorrectCredentialsException e) {
            message.put("error","密码不匹配");
            return  message;
        }
        Teacher login =  (Teacher)subject.getPrincipals().getPrimaryPrincipal();
        message.put("success","登录成功");
        message.put("id",login.getId());
        message.put("loginUser",login.getName());
        return message;
    }

    public Object register(Map<String,Object> user) {
        String s = JSONObject.toJSONString(user);
        Teacher teacher = JSONObject.parseObject(s, Teacher.class);
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(30);
        teacher.setSalt(randomAlphabetic);
        Object password = new SimpleHash(hashAlgorithmName, teacher.getPassword(), randomAlphabetic, hashIterations);
        teacher.setPassword(password.toString());
        return teacherMapper.insertTeacher(teacher)>0;
    }

    public Integer isOnlyAccount(Map<String,Object> conditions) {
        Object teacherAccount = conditions.get("teacherAccount");
        conditions.clear();
        conditions.put("account",teacherAccount);
        return teacherMapper.selectTeachers(conditions).size();
    }
}
