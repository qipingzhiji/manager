package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.Course;
import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.response.Message;
import com.weiqiaoshiyan.student.manager.service.CourseService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ResponseBody
    @RequestMapping("/courseInfo")
    public List<Course> selectCouserInfo(Map<String,Object> conditions) {
        List<Course> courses = courseService.selectCourseInfo(conditions);
        return courses;
    }

    @ResponseBody
    @DeleteMapping("/course/{id}")
    public Object deleteCourse(@PathVariable("id") int id){
        Integer[] integers = new Integer[1];
        integers[0] = id;
        boolean b = courseService.deleteCourse(integers);
        return b;
    }

    @ResponseBody
    @PostMapping("/course")
    public Object courseAdd(@RequestBody Course course,RedirectAttributes redirectAttributes) {
        /*if(StringUtils.isEmpty(course.getCourseName())){
            redirectAttributes.addAttribute("msg","填加的课程名称不能为空");
            return "redirect:/courses";
        }
        if(StringUtils.isEmpty(course.getCourseTime())){
            redirectAttributes.addAttribute("msg","课程时长不能为空");
            return "redirect:/courses";
        }
        if (courseService.insertCourse(course)) {
            redirectAttributes.addAttribute("msg","课程信息添加成功");
            return "redirect:/courses";
        }
        redirectAttributes.addAttribute("msg","课程信息添加失败");
        return "redirect:/courses";*/

        boolean b = courseService.insertCourse(course);
        if(b){
            return new Message("success","填加信息成功");
        }else {
            return new Message("failed","填加信息失败");
        }
    }

    @ResponseBody
    @PutMapping("/course")
    public Object course_update(@RequestBody Course  course, RedirectAttributes redirectAttributes) {
        /*if(courseService.updateCourse(course)){
            redirectAttributes.addAttribute("msg","课程信息更新成功");
            return "redirect:/courses";
        }
        redirectAttributes.addAttribute("msg","课程信息更新失败");
        return "redirect:/courses";*/
        boolean b = courseService.updateCourse(course);
        if(b){
            return new Message("success","更新信息成功");
        }else {
            return new Message("failed","更新信息失败");
        }
    }

    @GetMapping("courses")
    public  Object courseInfo(@RequestParam(name = "pageIndex",defaultValue = "1") Integer pageIndex,
                              @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,@RequestParam Map<String,Object> conditions,Model model,@RequestParam(name = "loginId",required = false) Integer id,@ModelAttribute("msg") String msg) {

        PageHelper.startPage(pageIndex,pageSize);
        conditions.put("teacherId",(id == null? ((Teacher)SecurityUtils.getSubject().getSession().getAttribute("loginUser")).getId():id));
        conditions.put("courseName",conditions.get("courseName1"));
        conditions.remove("courseName1");
        List<Course> courses = courseService.selectCourseInfo(conditions);
        PageInfo<Course> coursePageInfo = new PageInfo<>(courses);
        model.addAttribute("msg",msg);
        model.addAttribute("courses",coursePageInfo);
        return "teacher/courseInfo";
    }

}
