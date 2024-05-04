package com.example.courseStudentManagement.service;

import com.example.courseStudentManagement.model.Course;
import com.example.courseStudentManagement.model.Student;

import java.util.List;

public interface CourseService {
    public List<Course> getAllCourses();
    public Course getCourseById(int id);
    public Course insertCourse(Course course);
    public Course updateCourse(Course course);
    public void deleteCourse(int id);
    public Course insertStudentToCourse(Student student, Course course, float grade);
    public List<Student> getListStudentByCourse(int courseId);
    public void removeStudentFromCourse(Student student, Course course);
    boolean isStudentInCourse(Student student, Course course);
    public List<Course> getCoursesByYear(int year);
    public List<Course> getCoursesByName(String name);
    public List<Course> findAllSortByNameAndYear(String sortType, String year);
}
