package com.example.courseStudentManagement.controller;

import com.example.courseStudentManagement.model.Course;
import com.example.courseStudentManagement.service.CourseService;
import com.example.courseStudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LayoutController {
    StudentService studentService;
    CourseService courseService;

    @Autowired
    public LayoutController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalStudents", studentService.getAllStudents().stream().count());
        model.addAttribute("totalCourses", courseService.getAllCourses().stream().count());
        model.addAttribute("page", "dashboard");
        return "layout";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalStudents", studentService.getAllStudents().stream().count());
        model.addAttribute("totalCourses", courseService.getAllCourses().stream().count());
        List<Course> courses = courseService.getAllCourses();
        courses.sort((c1, c2) -> c2.getYear() - c1.getYear());
        model.addAttribute("courses", courses.subList(0, Math.min(5, courses.size())));
        model.addAttribute("page", "dashboard");
        return "layout";
    }


}
