package com.example.courseStudentManagement.DAO;

import com.example.courseStudentManagement.model.CourseStudent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CourseStudentDAOImp implements CourseStudentDAO{
    private EntityManager entityManager;

    @Autowired
    public CourseStudentDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CourseStudent> getAllCourseStudents() {
        String jpql = "SELECT cs FROM CourseStudent cs";
        Query query = entityManager.createQuery(jpql, CourseStudent.class);
        return query.getResultList();
    }

    @Override
    public CourseStudent getCourseStudentById(int id) {
        return entityManager.find(CourseStudent.class, id);
    }

    @Override
    public CourseStudent insertCourseStudent(CourseStudent courseStudent) {
        entityManager.persist(courseStudent);
        return courseStudent;
    }

    @Override
    public CourseStudent updateCourseStudent(CourseStudent courseStudent) {
        courseStudent = entityManager.merge(courseStudent);
        entityManager.flush();
        return courseStudent;
    }

    @Override
    public void deleteCourseStudent(CourseStudent courseStudent) {
        entityManager.remove(courseStudent);
    }

    @Override
    public List<CourseStudent> getCourseStudentByStudentId(int studentId) {
        String jpql = "SELECT cs FROM CourseStudent cs WHERE cs.student.id =:studentId";
        Query query = entityManager.createQuery(jpql, CourseStudent.class)
                .setParameter("studentId", studentId);
        return query.getResultList();
    }

    @Override
    public List<CourseStudent> getCoursesStudentByYear(int year) {
        String jpql = "SELECT cs FROM CourseStudent cs WHERE cs.course.year =:year";
        Query query = entityManager.createQuery(jpql, CourseStudent.class)
                .setParameter("year", year);
        return query.getResultList();
    }

    @Override
    public List<CourseStudent> getCourseStudentByCourseId(int courseId) {
        String jpql = "SELECT cs FROM CourseStudent cs WHERE cs.course.id =:courseId";
        Query query = entityManager.createQuery(jpql, CourseStudent.class)
                .setParameter("courseId", courseId);
        return query.getResultList();
    }

    @Override
    public List<String> getYears() {
        String jpql = "SELECT DISTINCT cs.course.year FROM CourseStudent cs order by cs.course.year asc";
        Query query = entityManager.createQuery(jpql);
        return query.getResultList();
    }

    @Override
    public void deleteCourseStudentByStudentId(int studentId) {
        String jpql = "DELETE FROM CourseStudent cs WHERE cs.student.id =:studentId";
        Query query = entityManager.createQuery(jpql)
                .setParameter("studentId", studentId);
        query.executeUpdate();
    }

    @Override
    public void deleteCourseStudentByCourseId(int courseId) {
        String jpql = "DELETE FROM CourseStudent cs WHERE cs.course.id =:courseId";
        Query query = entityManager.createQuery(jpql)
                .setParameter("courseId", courseId);
        query.executeUpdate();
    }
}
