package com.weiqiaoshiyan.student.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiqiaoshiyan.student.manager.entity.Course;
import com.weiqiaoshiyan.student.manager.entity.Teacher;
import com.weiqiaoshiyan.student.manager.service.CourseService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/course")
    public Object courseAdd(Course course,RedirectAttributes redirectAttributes) {
        if (courseService.insertCourse(course)) {
            redirectAttributes.addAttribute("msg","课程信息添加成功");
            return "redirect:/courses";
        }
        redirectAttributes.addAttribute("msg","课程信息添加失败");
        return "redirect:/courses";
    }

    @PutMapping("/course")
    public Object course_update(Course  course, RedirectAttributes redirectAttributes) {
        if(courseService.updateCourse(course)){
            redirectAttributes.addAttribute("msg","课程信息更新成功");
            return "redirect:/courses";
        }
        redirectAttributes.addAttribute("msg","课程信息更新失败");
        return "redirect:/courses";

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
