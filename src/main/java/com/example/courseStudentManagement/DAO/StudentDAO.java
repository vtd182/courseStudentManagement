package com.example.courseStudentManagement.DAO;

import com.example.courseStudentManagement.model.Student;

import java.util.List;

public interface StudentDAO {
    public Student insertStudent(Student student);
    public Student updateStudent(Student student);
    public void deleteStudent(Student student);
    public Student getStudent(int id);
    public List<Student> getAllStudents();
    public List<Student> getStudentsByName(String name);
    public List<Student> getAllStudentsSortByName(String sortType);
}
