package com.example.courseStudentManagement.controller;


import com.example.courseStudentManagement.model.Course;
import com.example.courseStudentManagement.model.Student;
import com.example.courseStudentManagement.service.CourseService;
import com.example.courseStudentManagement.service.StudentService;
import com.example.courseStudentManagement.model.CourseStudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {
    private CourseService courseService;
    private StudentService studentService;

    @Autowired
    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }


    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        List<String> years = studentService.getYears(); // get years from database
        model.addAttribute("years", years);
        model.addAttribute("page", "courses/courses");
        return "layout";
    }

    @GetMapping("courses/{id}")
    public String getCourseById(@PathVariable int id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            throw new RuntimeException("Course with id " + id + " not found.");
        }
        model.addAttribute("course", course);
        List<Student> students = courseService.getListStudentByCourse(id);
        model.addAttribute("students", students);
        model.addAttribute("page", "courses/course_information");
        return "layout";
    }

    @PostMapping("/courses")
    public String insertCourse(@ModelAttribute Course course, Model model) {
        course.setId(0);
        courseService.insertCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete course");
            return "redirect:/courses/" + id;
        }
        return "redirect:/courses";
    }

    @PostMapping("/courses/{id}")
    public String updateCourse(@PathVariable("id") int id, @Valid Course course, BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("course", course);
            return "redirect:/courses/" + id;
        }

        Course existingCourse = courseService.getCourseById(id);

        existingCourse.setName(course.getName());
        existingCourse.setLecture(course.getLecture());
        existingCourse.setYear(course.getYear());
        existingCourse.setNotes(course.getNotes());

        courseService.updateCourse(existingCourse);
        return "redirect:/courses/" + id;
    }

    @PostMapping("courses/{courseId}/remove-student/{studentId}")
    public String removeStudentFromCourse(@PathVariable("courseId") int courseId,
                                          @PathVariable("studentId") int studentId
            , RedirectAttributes redirectAttributes) {
        Course course = courseService.getCourseById(courseId);
        Student student = studentService.getStudentById(studentId);
        courseService.removeStudentFromCourse(student, course);
        redirectAttributes.addFlashAttribute("message", "Student removed from course successfully.");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/courses/{id}/check-student/{studentId}")
    public String checkStudent(@PathVariable("id") int id, @PathVariable("studentId") int studentId, Model model) {
        Course course = courseService.getCourseById(id);
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            model.addAttribute("message", "Student not found");
            return "message";
        }
        boolean isStudentInCourse = courseService.isStudentInCourse(student, course);
        if (isStudentInCourse) {
            model.addAttribute("message", "Student is in course");
        } else {
            model.addAttribute("message", "Student is not in course");
        }
        return "message";
    }

    @PostMapping("courses/{id}/add-student")
    public String addStudentToCourse(@PathVariable("id") int courseId,
                                     @RequestParam("studentId") int studentId,
                                     RedirectAttributes redirectAttributes) {
        Course course = courseService.getCourseById(courseId);
        Student student = studentService.getStudentById(studentId);

        if (student == null) {
            redirectAttributes.addFlashAttribute("message", "Student not found");
            redirectAttributes.addFlashAttribute("messageType", "danger");
            return "redirect:/courses/" + courseId;
        }

        if (courseService.isStudentInCourse(student, course)) {
            redirectAttributes.addFlashAttribute("message", "Student is already in the course.");
            redirectAttributes.addFlashAttribute("messageType", "danger");
            return "redirect:/courses/" + courseId;
        }

        courseService.insertStudentToCourse(student, course);
        redirectAttributes.addFlashAttribute("message", "Student added to course successfully.");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/courses/search")
    public String searchCourses(@RequestParam String name, Model model) {
        List<Course> courses = courseService.getCoursesByName(name);
        model.addAttribute("courses", courses);
        model.addAttribute("page", "courses/courses");
        return "layout";
    }

    @GetMapping("/courses/filter")
    public String filterCourses(@RequestParam String year, Model model, RedirectAttributes redirectAttributes) {
        List<String> years = studentService.getYears(); // get years from database
        model.addAttribute("years", years);

        if (year.equals("all")) {
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses);
            model.addAttribute("page", "courses/courses");
            redirectAttributes.addFlashAttribute("year", year);
            return "layout"; // return view name
        }

        List<Course> courses = courseService.getCoursesByYear(Integer.parseInt(year));
        model.addAttribute("courses", courses);
        model.addAttribute("page", "courses/courses");
        redirectAttributes.addFlashAttribute("year", year);
        return "layout"; // return view name
    }

}
