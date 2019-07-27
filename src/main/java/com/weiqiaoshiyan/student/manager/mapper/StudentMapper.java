package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface StudentMapper {
    @Insert("insert into student_detail(class_num, student_name, begin_time,course_id,student_id,teacher_id) values(#{classNum},#{studentName},#{beginTime},#{courseId},#{studentId},#{teacherId})")
    int insert(Student student);

    @Select("select * from student_detail")
    List<Student> selectAll();

    @Select("<script>" +
            "select * from student_detail" +
            "<where>" +
            "<if test='id != null'>" +
            "and id =  #{id}" +
            "</if>" +
            "<choose>" +
            "<when test='isStudent!=null'>" +
            "<if test='studentName != null and id ==null'>" +
            "<bind name='name_like' value= \"'%' +studentName + '%' \" />" +
            "and student_name like  #{name_like} " +
            "</if>" +
            "</when>" +
            "<otherwise>" +
            "<if test='studentName !=null'>" +
            "and student_name = #{studentName}" +
            "</if>" +
            "</otherwise>" +
            "</choose>" +

            "<if test='beginTime != null and id==null'>" +
            "and strftime('%Y-%m-%d', begin_time) = #{beginTime}" +
            "</if>" +
            "<if test='courseId != null and id == null'>" +
            "and course_id =#{courseId}" +
            "</if>" +
            "<if test='studentId != null'>" +
            "and student_id =#{studentId}" +
            "</if>" +
            "</where>" +
            "order by begin_time desc" +
            "</script>")
    /**
     * private Integer id;
     private String classNum;
     private  String studentName;
     private Date beginTime;
     private Integer courseId;
     private Integer studentId;
     private Integer teacherId;
     private Course course;
     private Teacher teacher;
     */
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "class_num",property = "classNum"),
            @Result(column = "student_name",property = "studentName"),
            @Result(column = "begin_time",property = "beginTime"),
            @Result(column = "course_id",property = "courseId"),
            @Result(column = "student_id",property = "studentId"),
            @Result(column = "teacher_id",property = "teacherId"),
            @Result(column = "course_id",property = "course",one = @One(select = "com.weiqiaoshiyan.student.manager.mapper.CourseMapper.selectCousreById")),
            @Result(column = "teacher_id",property ="teacher",one = @One(select = "com.weiqiaoshiyan.student.manager.mapper.TeacherMapper.selectTeacherById"))
    })
    List<Student> selectByCondition(Map<String, Object> conditions);

    @Delete("<script>" +
            "delete from student_detail where id in" +
            "<foreach collection='ids' open='(' close=')' separator=',' item='item' >" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int delete(@Param("ids") Integer[] ids);

    @Update("<script>" +
            "update student_detail " +
            "<set>" +
            "<if test = 'classNum !=null'>class_num = #{classNum}</if>" +
            "<if test = 'studentName !=null' >student_name = #{studentName}</if>" +
            "<if test = 'courseId !=null'>course_id = #{courseId} </if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>")
    int update(Student student);
}
