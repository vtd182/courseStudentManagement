package com.example.courseStudentManagement.DAO;

import com.example.courseStudentManagement.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImp implements StudentDAO {
    private EntityManager entityManager;

    @Autowired
    public StudentDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Student insertStudent(Student student) {
        entityManager.persist(student);
        return student;
    }

    @Override
    public Student updateStudent(Student student) {
        student = entityManager.merge(student);
        entityManager.flush();
        return student;
    }

    @Override
    public void deleteStudent(Student student) {
        entityManager.remove(student);
    }

    @Override
    public Student getStudent(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> getAllStudents() {
        String jpql = "SELECT s FROM Student s";
        List<Student> result = entityManager.createQuery(jpql, Student.class).getResultList();
        return result;
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        String jpql = "SELECT s FROM Student s WHERE s.name like :name";
        List<Student> result = entityManager.createQuery(jpql, Student.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
        return result;
    }

    @Override
    public int updateAllNameStudents(String name) {
        String jpql = "UPDATE Student s SET s.name =:name";
        Query query = entityManager.createQuery(jpql).setParameter("name", name);
        return query.executeUpdate();
    }

    @Override
    public List<Student> getAllStudentsSortByName(String sortType) {
        String jpql;
        if (sortType.equals("ASC")) {
            jpql = "SELECT s FROM Student s ORDER BY s.name ASC";
        } else {
            jpql = "SELECT s FROM Student s ORDER BY s.name DESC";
        }
        return entityManager.createQuery(jpql, Student.class).getResultList();
    }
}
