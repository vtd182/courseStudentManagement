package com.example.courseStudentManagement.service;

import com.example.courseStudentManagement.model.CourseStudent;
import com.example.courseStudentManagement.model.Course;
import com.example.courseStudentManagement.DAO.CourseDAO;
import com.example.courseStudentManagement.DAO.CourseStudentDAO;
import com.example.courseStudentManagement.DAO.StudentDAO;
import com.example.courseStudentManagement.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService{
    CourseDAO courseDAO;
    CourseStudentDAO courseStudentDAO;

    StudentDAO studentDAO;

    @Autowired
    public CourseServiceImp(CourseDAO courseDAO, CourseStudentDAO courseStudentDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.courseStudentDAO = courseStudentDAO;
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    @Override
    public Course getCourseById(int id) {
        return courseDAO.getCourseById(id);
    }

    @Override
    @Transactional
    public Course insertCourse(Course course) {
        return courseDAO.insertCourse(course);
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        return courseDAO.updateCourse(course);
    }

    @Override
    @Transactional
    public void deleteCourse(int id) {
        Course course = courseDAO.getCourseById(id);
        courseStudentDAO.deleteCourseStudentByCourseId(course.getId());
        courseDAO.deleteCourse(course);
    }

    @Override
    @Transactional
    public Course insertStudentToCourse(Student student, Course course) {
        CourseStudent courseStudent;
        courseStudent = new CourseStudent();
        courseStudent.setCourse(course);
        courseStudent.setStudent(student);
        courseStudentDAO.insertCourseStudent(courseStudent);
        return course;
    }

    @Override
    public List<Student> getListStudentByCourse(int courseId) {
        List<CourseStudent> courseStudents = courseStudentDAO.getCourseStudentByCourseId(courseId);
        List<Student> students =  new java.util.ArrayList<>();
        for (CourseStudent courseStudent : courseStudents) {
            students.add(courseStudent.getStudent());
        }
        return students;
    }

    @Override
    @Transactional
    public void removeStudentFromCourse(Student student, Course course) {
        List<CourseStudent> courseStudents = courseStudentDAO.getCourseStudentByStudentId(student.getId());
        for (CourseStudent courseStudent : courseStudents) {
            if (courseStudent.getCourse().getId() == course.getId()) {
                courseStudentDAO.deleteCourseStudent(courseStudent);
            }
        }
    }

    @Override
    public boolean isStudentInCourse(Student student, Course course) {
        List<CourseStudent> courseStudents = courseStudentDAO.getCourseStudentByStudentId(student.getId());
        for (CourseStudent courseStudent : courseStudents) {
            if (courseStudent.getCourse().getId() == course.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Course> getCoursesByYear(int year) {
        return courseDAO.getCoursesByYear(year);
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseDAO.getCoursesByName(name);
    }
}
