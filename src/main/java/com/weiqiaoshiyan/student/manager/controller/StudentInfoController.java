package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.StudentInfoMapper;
import com.weiqiaoshiyan.student.manager.response.Message;
import com.weiqiaoshiyan.student.manager.service.StudentInfoService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhang_htao on 2019/8/5.
 */
@RestController
public class StudentInfoController {

    @Value("${mysharo.hashIterations}")
    private int hashIterations;
    @Value("${mysharo.hashAlgorithmName}")
    private String hashAlgorithmName;

    @Autowired
    private StudentInfoService service;
    @Autowired
    private StudentInfoMapper studentInfoDao;


    @PutMapping("studentInfo")
    public Object updateStudentInfo(@RequestBody StudentInfo info){
        if(!StringUtils.isEmpty(info.getPassword())){
            StudentInfo studentInfo = studentInfoDao.listById(info.getId());
            String salt = studentInfo.getSalt();
            info.setPassword(new SimpleHash(hashAlgorithmName,info.getPassword(),salt,hashIterations).toString());
        }
        if (service.updateStudentInfo(info)) {
            return new Message("success","学生信息更新成功");
        } else{
            return new Message("failed","学生信息更新失败");
        }
    }

    @PostMapping("studentInfo")
    public Object addStudentInfo(@RequestBody StudentInfo info){
        if (service.studentRegister(info)) {
            return new Message("success","学生信息填加成功");
        }else {
            return new Message("failed","学生信息填加失败");
        }
    }

    @DeleteMapping("studentInfo")
    public Object deleteStudentInfo(@RequestBody Integer[] ids) {
        if (studentInfoDao.delete(ids)>0) {
            return new Message("success","学生信息删除成功");
        }else {
            return new Message("failed","学生信息删除失败");
        }
    }
}
