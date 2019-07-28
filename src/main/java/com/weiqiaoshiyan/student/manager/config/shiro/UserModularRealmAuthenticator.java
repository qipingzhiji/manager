package com.weiqiaoshiyan.student.manager.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhang_htao on 2019/7/28.
 */
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        StudentAndTeacherUsernamePasswordToken studentAndTeacherUsernamePasswordToken= (StudentAndTeacherUsernamePasswordToken)authenticationToken;
        String logintype = studentAndTeacherUsernamePasswordToken.getLogintype();
        Collection<Realm> realms = getRealms();
        List<Realm> realmList = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().equals(studentAndTeacherUsernamePasswordToken.getLogintype())) {
                realmList.add(realm);
            }
        }
        if(realmList.size() == 1){
            return doSingleRealmAuthentication(realmList.get(0),studentAndTeacherUsernamePasswordToken);
        }else {
            return  doMultiRealmAuthentication(realmList,studentAndTeacherUsernamePasswordToken);
        }
    }
}
