package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Mapper
@Repository
public interface StudentInfoMapper {

    @Insert("insert into student_info(student_number,student_name,password,class_id,create_time,sex) values(#{studentNumber},#{studentName},#{password},#{classId},#{createTime},#{sex})")
    int insert(StudentInfo studentInfo);
    @Delete("<script>" +
            "delte from student_info where id in" +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int delete(@Param("id") Integer[] ids);

    @Update("<script>" +
            "update student_info" +
            "<set>" +
            "<if test='studentNumber != null'>" +
            "student_number = #{studentNumber}" +
            "</if>" +
            "<if test ='password!=null'>" +
            "password=#{password}" +
            "</if>" +
            "<if test='studentName!=null'>" +
            "student_name = #{studentName}" +
            "</if>" +
            "<if test='classId != null'>" +
            "class_info = #{classId}" +
            "</if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>")
    int update(StudentInfo studentInfo);

    @Select("<script>" +
            "select * from student_info " +
            "<where>" +
            "<choose>" +
            "<when test = 'id!=null'>" +
            "and id = #{id}" +
            "</when>" +
            "<otherwise>" +
            "<if test='studentName!=null'>" +
            "<bind name = 'likeStudentName' value = \"+ '%' + studentName+'%'\" />" +
            "and student_name like #{likeStudentName}" +
            "</if>" +
            "<if test='studentNumber!=null'>" +
            "<bind name='likeStudentNubmer' value=\"+'%'+studentNumber+'%'\" />" +
            "and student_number like #{likeStudentNubmer}" +
            "</if>" +
            "<if test='class_id !=null'>" +
            "and class_id = #{classId}" +
            "</if>" +
            "</otherwise>" +
            "</choose>" +
            "</where>" +
            "</script>")
    List<StudentInfo> listByConditon(Map<String, Object> collections);
}
