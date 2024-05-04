package com.example.courseStudentManagement.DAO;

import com.example.courseStudentManagement.model.Course;

import java.util.List;

public interface CourseDAO {
    public List<Course> getAllCourses();
    public Course getCourseById(int id);
    public Course insertCourse(Course course);
    public Course updateCourse(Course course);
    public void deleteCourse(Course course);
    public List<Course> getCoursesByYear(int year);
    public List<Course> getCoursesByName(String name);
    public List<Course> getAllCoursesSortByName(String sortType);
}
