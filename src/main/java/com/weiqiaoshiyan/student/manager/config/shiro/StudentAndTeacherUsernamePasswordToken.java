package com.weiqiaoshiyan.student.manager.config.shiro;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by zhang_htao on 2019/7/28.
 */
public class StudentAndTeacherUsernamePasswordToken extends  UsernamePasswordToken{
    @Getter
    @Setter
    private  String  Logintype;

    public StudentAndTeacherUsernamePasswordToken(String username, String password, String Logintype) {
        super(username, password);
        this.Logintype = Logintype;
    }


}
