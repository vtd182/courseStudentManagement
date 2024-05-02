package com.example.courseStudentManagement.controller;

import com.example.courseStudentManagement.model.CourseStudent;
import com.example.courseStudentManagement.model.Student;
import com.example.courseStudentManagement.service.StudentService;

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
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        List<String> years = studentService.getYears(); // get years from database
        model.addAttribute("years", years);
        model.addAttribute("page", "students/students");
        Student student = new Student();
        model.addAttribute("student", student);
        return "layout";
    }

    @GetMapping("/students/search")
    public String searchStudents(@RequestParam String name, Model model) {
        List<Student> students = studentService.searchStudentsByName(name);
        model.addAttribute("students", students);
        model.addAttribute("page", "students/students");
        return "layout";
    }

    @GetMapping("/students/filter")
    public String filterStudents(@RequestParam String sortType, @RequestParam String year, Model model) {
        List<String> years = studentService.getYears(); // get years from database
        model.addAttribute("years", years);
        List<Student> students = studentService.findAllSortByNameAndYear(sortType, year);
        model.addAttribute("students", students);
        model.addAttribute("page", "students/students");

        model.addAttribute("sortType", sortType);
        model.addAttribute("year", year);

        return "layout"; // return view name
    }

    @GetMapping("students/{id}")
    public String viewStudent(@PathVariable("id") int id, Model model) {
        Student student = studentService.getStudentById(id);
        List<CourseStudent> courseStudents = studentService.getListCourseStudentByStudent(id);
        model.addAttribute("courseStudents", courseStudents);
        model.addAttribute("student", student);
        model.addAttribute("page", "students/student_information");
        if (student != null) {
            // If the student is found, add it to the model
            model.addAttribute("student", student);
        } else {
            // If the student is not found, add an empty Student object to the model
            model.addAttribute("student", new Student());
        }
        return "layout";
    }

    @GetMapping("students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete student");
            return "redirect:/students/" + id;
        }
        return "redirect:/students";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable("id") int id, @Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("student", student);
            return "redirect:/students/" + id;
        }

        Student existingStudent = studentService.getStudentById(id);

        existingStudent.setName(student.getName());
        existingStudent.setBirthDate(student.getBirthDate());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setNotes(student.getNotes());

        studentService.updateStudent(existingStudent);
        return "redirect:/students/" + id;
    }

    @PostMapping("/students")
    public String insertStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("student", student);
            return "redirect:/students";
        }

        student.setId(0);
        studentService.insertStudent(student);
        return "redirect:/students";
    }
//    @PostMapping("/students")
//    public ResponseEntity<Student> insertStudent(@RequestBody Student student) {
//        System.out.println(student);
//        student.setId(0);
//        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.insertStudent(student));
//    }


//    @PostMapping
//    public ResponseEntity<Student> insertStudent(@RequestBody Student student) {
//        student.setId(0);
//        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.insertStudent(student));
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity<Student> updateStudentById(@PathVariable int id, @RequestBody Student student) {
//        Student existingStudent = studentService.getStudentById(id);
//        if (existingStudent == null) {
//            throw new RuntimeException("Student with id " + id + " not found.");
//        } else {
//            existingStudent.setName(student.getName());
//            existingStudent.setAddress(student.getAddress());
//            existingStudent.setBirthDate(student.getBirthDate());
//            existingStudent.setNotes(student.getNotes());
//            return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(existingStudent));
//        }
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> deleteStudentById(@PathVariable int id) {
//        Student student = studentService.getStudentById(id);
//        if (student == null) {
//            throw new RuntimeException("Student with id " + id + " not found.");
//        }
//        studentService.deleteStudent(student);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @GetMapping("/year/{year}")
//    public ResponseEntity<List<Student>> getListStudentByYear(@PathVariable int year) {
//        return ResponseEntity.ok().body(studentService.getListStudentByYear(year));
//    }
//
//    @GetMapping("{id}/courses")
//    public ResponseEntity<List<CourseStudent>> getListCourseStudentByStudent(@PathVariable int id) {
//        Student student = studentService.getStudentById(id);
//        if (student == null) {
//            throw new RuntimeException("Student with id " + id + " not found.");
//        }
//        return ResponseEntity.ok().body(studentService.getListCourseStudentByStudent(id));
//    }

}
