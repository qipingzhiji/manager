package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface StudentMapper {
    @Insert("insert into student(class_num, student_name, begin_time,course_id) values(#{classNum},#{studentName},#{beginTime},#{courseId})")
    int insert(Student student);

    @Select("select * from student")
    List<Student> selectAll();

    @Select("<script>" +
            "select * from student" +
            "<where>" +
            "<if test='id != null'>" +
            "and id =  #{id}" +
            "</if>" +
            "<if test='studentName != null and id ==null'>" +
            "<bind name='name_like' value= \"'%' +studentName + '%' \" />" +
            "and student_name like  #{name_like} " +
            "</if>" +
            "<if test='beginTime != null and id==null'>" +
            "and begin_time = #{beginTime}" +
            "</if>" +
            "<if test='courseId != null and id == null'>" +
            "and course_id =#{courseId}" +
            "</if>" +
            "</where>" +
            "</script>")
    List<Student> selectByCondition(Map<String, Object> conditions);

    @Delete("<script>" +
            "delete from student where id in" +
            "<foreach collection='ids' open='(' close=')' separator=',' item='item' >" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int delete(@Param("ids") Integer[] ids);

    @Update("<script>" +
            "update student " +
            "<set>" +
            "<if test = 'classNum !=null'>class_num = #{classNum}</if>" +
            "<if test = 'studentName !=null' >student_name = #{studentName}</if>" +
            "<if test = 'courseId !=null'>course_id = #{courseId} </if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>")
    int update(Student student);
}
