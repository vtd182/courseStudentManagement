package com.example.courseStudentManagement.service;

import com.example.courseStudentManagement.model.CourseStudent;
import com.example.courseStudentManagement.DAO.CourseStudentDAO;
import com.example.courseStudentManagement.DAO.StudentDAO;
import com.example.courseStudentManagement.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImp implements StudentService{
    private StudentDAO studentDAO;
    private CourseStudentDAO courseStudentDAO;

    @Autowired
    public StudentServiceImp(StudentDAO studentDAO, CourseStudentDAO courseStudentDAO) {
        this.studentDAO = studentDAO;
        this.courseStudentDAO = courseStudentDAO;
    }
    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public Student getStudentById(int id) {
        return studentDAO.getStudent(id);
    }

    @Override
    @Transactional
    public Student insertStudent(Student student) {
        return studentDAO.insertStudent(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {
        return studentDAO.updateStudent(student);
    }

    @Override
    @Transactional
    public void deleteStudent(int id) {
        courseStudentDAO.deleteCourseStudentByStudentId(id);
        Student student = studentDAO.getStudent(id);
        studentDAO.deleteStudent(student);
    }

    @Override
    public List<Student> getListStudentByYear(int year) {
        List<CourseStudent> courseStudents = courseStudentDAO.getCoursesStudentByYear(year);
        Set<Student> students = new HashSet<>();
        for (CourseStudent courseStudent : courseStudents) {
            students.add(courseStudent.getStudent());
        }
        return students.stream().toList();
    }

    @Override
    public List<CourseStudent> getListCourseStudentByStudent(int id) {
        return courseStudentDAO.getCourseStudentByStudentId(id);
    }

    @Override
    public List<Student> searchStudentsByName(String name) {
        return studentDAO.getStudentsByName(name);
    }

    @Override
    public List<Student> sortStudentsByName(String sortType) {
        return studentDAO.getAllStudentsSortByName(sortType);
    }

    @Override
    public List<Student> findAllSortByNameAndYear(String sortType, String year) {
        if (year.equals("all")) {
            return studentDAO.getAllStudentsSortByName(sortType);
        } else {
            Set<Student> students = new HashSet<>();
            courseStudentDAO.getCoursesStudentByYear(Integer.parseInt(year)).stream()
                    .map(CourseStudent::getStudent)
                    .forEach(students::add);
            List<Student> sortedStudents;
            if (sortType.equals("ASC")) {
                sortedStudents = students.stream().sorted((s1, s2) -> s1.getName().compareTo(s2.getName())).toList();
            } else {
                sortedStudents = students.stream().sorted((s1, s2) -> s2.getName().compareTo(s1.getName())).toList();
            }
            return sortedStudents;
        }
    }

    @Override
    public List<String> getYears() {
        return courseStudentDAO.getYears();
    }
}
