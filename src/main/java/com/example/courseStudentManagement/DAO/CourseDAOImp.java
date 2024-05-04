package com.example.courseStudentManagement.DAO;

import com.example.courseStudentManagement.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CourseDAOImp implements CourseDAO {
    private EntityManager entityManager;

    @Autowired
    public CourseDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Course> getAllCourses() {
        String jpql = "SELECT c FROM Course c";
        Query query = entityManager.createQuery(jpql, Course.class);
        return query.getResultList();
    }

    @Override
    public Course getCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    public Course insertCourse(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    public Course updateCourse(Course course) {
        course = entityManager.merge(course);
        entityManager.flush();
        return course;
    }

    @Override
    public void deleteCourse(Course course) {
        entityManager.remove(course);
    }

    @Override
    public List<Course> getCoursesByYear(int year) {
        String jpql = "SELECT c FROM Course c WHERE c.year = :year";
        Query query = entityManager.createQuery(jpql, Course.class);
        query.setParameter("year", year);
        return query.getResultList();
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        String jpql = "SELECT c FROM Course c WHERE c.name like :name";
        Query query = entityManager.createQuery(jpql, Course.class)
                .setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Course> getAllCoursesSortByName(String sortType) {
        if (sortType.equals("ASC")) {
            String jpql = "SELECT c FROM Course c ORDER BY c.name ASC";
            Query query = entityManager.createQuery(jpql, Course.class);
            return query.getResultList();
        } else {
            String jpql = "SELECT c FROM Course c ORDER BY c.name DESC";
            Query query = entityManager.createQuery(jpql, Course.class);
            return query.getResultList();
        }
    }

}
