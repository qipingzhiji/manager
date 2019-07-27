package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.StudentInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Mapper
@Repository
public interface StudentInfoMapper {

    @Insert("insert into student_info(student_number,student_name,password,class_id,create_time,sex,salt) values(#{studentNumber},#{studentName},#{password},#{classId},'datetime()',#{sex},#{salt})")
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
            "student_number = #{studentNumber}," +
            "</if>" +
            "<if test ='password!=null'>" +
            "password=#{password}," +
            "</if>" +
            "<if test='studentName!=null'>" +
            "student_name = #{studentName}," +
            "</if>" +
            "<if test='classId != null'>" +
            "class_info = #{classId}," +
            "</if>" +
            "<if test='password !=null'>" +
            "password=#{password}," +
            "</if>" +
            "<if test='sex!=null'>" +
            "sex = #{sex}" +
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
            "<choose>" +
            "<when test='login != null'>" +
            " and student_name =#{studentName}" +
            "</when>" +
            "<otherwise>" +
            "<if test='studentName!=null'>" +
            "<bind name = 'likeStudentName' value = \"+ '%' + studentName+'%'\" />" +
            " and student_name like #{likeStudentName}" +
            "</if>" +
            "</otherwise>" +
            "</choose>" +
            "<if test='studentNumber!=null'>" +
            "<bind name='likeStudentNubmer' value=\"+'%'+studentNumber+'%'\" />" +
            " and student_number like #{likeStudentNubmer}" +
            "</if>" +
            "<if test='password!=null'>" +
            "and password=#{password}" +
            "</if>" +
            "<if test='classId !=null'>" +
            "and class_id = #{classId}" +
            "</if>" +
            "<if test='sex !=null'>" +
            "and sex = #{sex}" +
            "</if>" +
            "</otherwise>" +
            "</choose>" +
            "</where>" +
            "</script>")
    @ResultType(StudentInfo.class)
    @Results(
            {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "student_number",property = "studentNumber"),
                    @Result(column = "student_name",property = "studentName"),
                    @Result(column = "sex",property = "sex"),
                    @Result(column ="password",property = "password"),
                    @Result(column = "class_id",property = "classId"),
                    @Result(column = "create_time",property = "createTime"),
                    @Result(column = "class_id", property = "classInfo",one = @One(select = "com.weiqiaoshiyan.student.manager.mapper.ClassInfoMapper.selectById",fetchType = FetchType.LAZY )),
            }
    )
    List<StudentInfo> listByConditon(Map<String, Object> collections);

    /**
     * private Integer id;
     private String studentNumber;
     private String studentName;
     private Integer sex;
     private String password;
     private Integer classId;
     private Date createTime;
     private ClassInfo classInfo;
     */
}
