package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.Course;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
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
            "<when test='id != null'>" +
            "and id = #{id}" +
            "</when>" +
            "<otherwise>" +
            "<if test= 'courseName != null'>" +
            "<bind name='courseNameLike' value=\"'%' + courseName +'%'\" />" +
            "and course_name like #{courseNameLike}" +
            "</if>" +
            "<if test = 'teacherName != null'>" +
            "<bind name = 'like_teacherName' value=\" '%'+ teacherName + '%' \" />" +
            "and teacher_name like #{like_teacherName}" +
            "</if>" +
            "<if test='teacherId!=null'>" +
            "and teacher_id = #{teacherId}" +
            "</if>" +
            "</otherwise>" +
            "</choose>" +
            "</where>" +
            "</script>")
    @ResultType(Course.class)
    /**
     * private Integer id;
     private String courseName;
     private Integer teacherId;
     private Integer courseTime;
     private Teacher teacher;
     */
    @Results(value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "teacher_id",property = "teacherId"),
            @Result(column = "course_time",property = "courseTime"),
            @Result(column = "teacher_id",property = "teacher", one = @One(select = "com.weiqiaoshiyan.student.manager.mapper.TeacherMapper.selectTeacherById",fetchType = FetchType.EAGER))
    })
    List<Course> selectCourse(Map<String,Object> conditons);


    @Insert("insert into course(course_name,teacher_id,course_time,teacher_name) values(#{courseName},#{teacherId},#{courseTime},#{teacherName})")
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
            "course_name = #{courseName}," +
            "</if>" +
            "<if test='teacherId != null'>" +
            "teacher_id =#{teacherId}," +
            "</if>" +
            "<if test='courseTime!=null'>" +
            "course_time = #{courseTime}," +
            "</if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>")
    int updateCourse(Course course);

    @Select("select * from course where id = #{id}")
    Course selectCousreById(Integer id);
}
