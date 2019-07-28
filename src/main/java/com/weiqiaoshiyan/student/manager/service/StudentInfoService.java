package com.weiqiaoshiyan.student.manager.service;

import com.weiqiaoshiyan.student.manager.config.shiro.StudentAndTeacherUsernamePasswordToken;
import com.weiqiaoshiyan.student.manager.constant.LoginType;
import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.StudentInfoMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Service
public class StudentInfoService {
    @Autowired
    private StudentInfoMapper dao;

    @Value(value = "${mysharo.hashAlgorithmName}")
    private  String hashAlgorithmName;
    @Value(value = "${mysharo.hashIterations}")
    private int hashIterations;
    public boolean studentRegister(StudentInfo studentInfo) {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(30);
        studentInfo.setSalt(randomAlphabetic);
        studentInfo.setCreateTime(new Date());
        Object password = new SimpleHash(hashAlgorithmName, studentInfo.getPassword(), randomAlphabetic,hashIterations);
        studentInfo.setPassword(password.toString());
        studentInfo.setCreateTime(new Date());
        return dao.insert(studentInfo) > 0;
    }

    public boolean studentRegisterOnly(Map<String,Object> conditions){
        if(dao.listByConditon(conditions).size() > 0) {
            return false;
        }else {
            return  true;
        }
    }
    public StudentInfo getStudentInfoById(Integer id){
        Map<String,Object> condition = new HashMap<>();
        condition.put("id", id);
        return dao.listByConditon(condition).get(0);
    }


    public boolean updateStudentInfo(StudentInfo studentInfo) {
        return dao.update(studentInfo) > 0;
    }

    /**
     * 进行登录认证
     * @param conditions
     * @return
     */
    public Map<String,String> studentLogin(Map<String,Object> conditions) {
        Map<String,String> maps = new HashMap<>();
        //获取登录用户的subject
        Subject subject = SecurityUtils.getSubject();
        //设置token
        StudentAndTeacherUsernamePasswordToken token = new StudentAndTeacherUsernamePasswordToken(conditions.get("studentName").toString() + "_" +conditions.get("classId").toString() ,conditions.get("password").toString(), LoginType.STUDENT.getLogin());
        //通过设置的token来进行登录
        try {
            //下面一行代码代表开启了rememberMe功能
            // token.setRememberMe(true);
            subject.login(token);
        } catch (UnknownAccountException e) {
            //发生此异常说明用户名错误
            maps.put("errorFlag","true");
            maps.put("accountError","用户名错误");
            maps.put("errorPage","login");
            return maps;
        } catch (IncorrectCredentialsException e) {
            //发生此异常说明密码错误
            maps.put("errorFlag","true");
            maps.put("incorrectCredentialError","密码错误");
            maps.put("errorPage","login");
            return maps;
        }
        return maps;
    }
}
