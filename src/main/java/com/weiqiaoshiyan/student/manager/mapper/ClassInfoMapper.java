package com.weiqiaoshiyan.student.manager.mapper;

import com.weiqiaoshiyan.student.manager.entity.ClassInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhang_htao on 2019/7/24.
 */
@Mapper
@Repository
@CacheNamespace
public interface ClassInfoMapper {

    @Insert("insert into class_info(class_name,class_comment) values(#{className},#{classComment})")
    int insert(ClassInfo classInfo);

    @Update("<script>" +
            "update class_info " +
            "<set>" +
            "<if test='className !=null'>" +
            "class_name =#{className}" +
            "</if>" +
            "<if test='classComment !=null'>" +
            "class_comment=#{classComment}" +
            "</if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>")
    int update(ClassInfo classInfo);

    @Delete("<script>" +
            "delete from class_info where id in" +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int delete(@Param("ids") Integer[] ids);

    @Select("<script>" +
            "select * from class_info" +
            "<where>" +
            "<choose>" +
            "<when test='id!=null'>" +
            "and id =#{id}" +
            "</when>" +
            "<otherwise>" +
            "<if test='className!=null'>" +
            "<bind name='likeClassName' value=\" + '%'+ className+'%'\"  />" +
            "and class_name like #{likeClassName}" +
            "</if>" +
            "</otherwise>" +
            "</choose>" +
            "</where>" +
            "</script>")
    List<ClassInfo> selectClassInfoByConditions(Map<String,Object> conditions);

    @Select("select * from class_info where id  = #{classId}")
    ClassInfo selectById(Integer classId);
}
