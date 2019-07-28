package com.weiqiaoshiyan.student.manager.config.shiro;

import com.weiqiaoshiyan.student.manager.constant.LoginType;
import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.mapper.TeacherMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/28.
 */
public class TeacherRealm extends AuthorizingRealm{

    @Autowired
    private TeacherMapper dao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StudentAndTeacherUsernamePasswordToken loginToken =  (StudentAndTeacherUsernamePasswordToken)token;
        String username = loginToken.getUsername();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("account",username);
        List<Teacher> teachers = dao.selectTeachers(conditions);
        if(teachers.size()==0) {
            return  null;
        }
        ByteSource bytes = ByteSource.Util.bytes(teachers.get(0).getSalt());
        return new SimpleAuthenticationInfo(teachers.get(0),teachers.get(0).getPassword(),bytes, LoginType.TEACHER.getLogin());
    }
}
