package com.weiqiaoshiyan.student.manager.controller;

import com.weiqiaoshiyan.student.manager.mapper.ClassInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@RestController
public class ClassInfoController {
    @Autowired
    private ClassInfoMapper dao;

    @RequestMapping("/ClassInfos")
    public Object classInfoByConditions(@RequestBody(required = true) Map<String,Object> conditions){
        return dao.selectClassInfoByConditions(conditions);
    }
}
