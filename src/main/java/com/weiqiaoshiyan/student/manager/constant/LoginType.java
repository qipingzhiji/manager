package com.weiqiaoshiyan.student.manager.constant;

/**
 * Created by zhang_htao on 2019/7/28.
 */
public enum    LoginType {
    TEACHER("teacher","老师登录"),
    STUDENT("student","学生登录")
    ;
    private String login;
    private String desc;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    LoginType(String login, String desc) {
        this.login = login;
        this.desc = desc;
    }
}
