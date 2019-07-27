package com.weiqiaoshiyan.student.manager.config.shiro;

import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import com.weiqiaoshiyan.student.manager.mapper.StudentInfoMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/25.
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private StudentInfoMapper dao;

    /**
     * 进行授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 进行认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Map<String, Object> conditions = new HashMap<String,Object>();
        conditions.put("login",true);
        String[] split = token.getUsername().split("_");
        conditions.put("studentName",split[0]);
        conditions.put("classId",split[1]);
        List<StudentInfo> studentInfos = dao.listByConditon(conditions);
        if(studentInfos.size() ==0 ){
            return null;
        }
        ByteSource byteSource = ByteSource.Util.bytes(studentInfos.get(0).getSalt());
        return new SimpleAuthenticationInfo(studentInfos.get(0),studentInfos.get(0).getPassword(),byteSource,getName());
    }
}
