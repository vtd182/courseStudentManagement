package com.example.courseStudentManagement.DAO;

import com.example.courseStudentManagement.model.CourseStudent;

import java.util.List;

public interface CourseStudentDAO {
    List<CourseStudent> getAllCourseStudents();
    CourseStudent getCourseStudentById(int id);
    CourseStudent insertCourseStudent(CourseStudent courseStudent);
    CourseStudent updateCourseStudent(CourseStudent courseStudent);
    void deleteCourseStudent(CourseStudent courseStudent);
    List<CourseStudent> getCourseStudentByStudentId(int studentId);
    List<CourseStudent> getCoursesStudentByYear(int year);
    List<CourseStudent> getCourseStudentByCourseId(int courseId);
    List<String> getYears();
    void deleteCourseStudentByStudentId(int studentId);
    void deleteCourseStudentByCourseId(int courseId);
}
