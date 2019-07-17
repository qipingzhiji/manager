package com.weiqiaoshiyan.student.manager.sqlProvider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;


public class TeacherSqlProvider extends  SQL{

    public String selectWhere(Map<String, Object> conditons) {
        return new SQL() {
            {
                SELECT("*");
                FROM("teacher");
                if (conditons.size() > 0) {
                    if (!StringUtils.isEmpty(conditons.get("id"))) {
                        WHERE("id = #{id}");
                    } else {
                        if (!StringUtils.isEmpty(conditons.get("name"))) {
                            conditons.put("name", "%"+conditons.get("name")+"%");
                            WHERE("name like #{name}");
                        }
                    }
                }
            }
        }.toString();
    }

}
