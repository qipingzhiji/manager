package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CourseMapper {
    @Select("<script>" +
            "select * from course" +
            "<where>" +
            "<choose>" +
            "<when test='teacherId != null'>" +
            "and teacher_id = #{teacherId}" +
            "</when>" +
            "<otherwise>" +
            "<if test= 'courseName != null'>" +
            "<bind name='courseNameLike' value=\"'%' + courseName +'%'\" />" +
            "and course_name like #{courseNameLike}" +
            "</if>" +
            "</otherwise>" +
            "</choose>" +
            "</where>" +
            "</script>")
    List<Course> selectCourse(Map<String,Object> conditons);


    @Insert("insert into course(course_name,teacher_id) values(#{courseName},#{teacherId})")
    int insertCourse(Course course);

    @Delete("<script>" +
            "delete from course where id in" +
            "<foreach collection='ids' open='(' close=')' separator=',' item='item' >" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int deleteCourse(@Param("ids") Integer[] ids);

    @Update("<script>" +
            "update course " +
            "<set>" +
            "<if test= 'courseName !=null'>" +
            "course_name = #{courseName}" +
            "</if>" +
            "<if test='teacherId != null'>" +
            "teacher_id =#{teacherId}" +
            "</if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>")
    int updateCourse(Course course);
}
