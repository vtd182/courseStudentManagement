package com.example.courseStudentManagement.service;



import com.example.courseStudentManagement.model.CourseStudent;
import com.example.courseStudentManagement.model.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudents();
    public Student getStudentById(int id);
    public Student insertStudent(Student student);
    public Student updateStudent(Student student);
    public void deleteStudent(int id);
    public List<Student> getListStudentByYear(int year);
    public List<CourseStudent> getListCourseStudentByStudent(int id);
    public List<Student> searchStudentsByName(String name);
    public List<Student> sortStudentsByName(String sortType);
    public List<Student> findAllSortByNameAndYear(String sortType, String year);
    public List<String> getYears();
}
