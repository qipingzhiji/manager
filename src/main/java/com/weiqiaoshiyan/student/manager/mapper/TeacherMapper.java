package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.sqlProvider.TeacherSqlProvider;
import com.weiqiaoshiyan.student.manager.sqlProvider.TeacherUpdateProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TeacherMapper {
    @Insert("insert into teacher(name,account,password,perms) values(#{name},#{account},#{password},#{perms})")
    int insertTeacher(Teacher teacher);

    @Delete("<script>" +
            "delete from teacher where id in " +
            "<foreach collection ='ids' item = 'item' open='(' close=')' separator=','>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int deleteTeacher(@Param("ids") Integer[] ids);

    @SelectProvider(value = TeacherSqlProvider.class,method = "selectWhere")
    List<Teacher> selectTeachers(Map<String,Object> conditions);

    @UpdateProvider(value = TeacherUpdateProvider.class,method = "update")
    int updateTeacher(@Param("teacher") Teacher teacher);

    @Select("select * from teacher where id =#{id}")
    Teacher selectTeacherById(Integer id);

}
