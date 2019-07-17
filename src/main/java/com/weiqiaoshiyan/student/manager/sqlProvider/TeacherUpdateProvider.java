package com.weiqiaoshiyan.student.manager.sqlProvider;

import com.weiqiaoshiyan.student.manager.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class TeacherUpdateProvider extends  SQL{

    public String update(@Param("teacher")Teacher teacher){
        return  new SQL(){{
            UPDATE("teacher");
            if(!StringUtils.isEmpty(teacher.getName())) {
                SET("name = #{teacher.name,javaType=String,jdbcType=VARCHAR}");
            }
            if(!StringUtils.isEmpty(teacher.getAccount())) {
                SET("account = #{teacher.account}");
            }
            if(!StringUtils.isEmpty(teacher.getPassword())) {
                SET("password=#{teacher.password}");
            }
            WHERE("id = #{teacher.id}");
        }}.toString();
    }
}
