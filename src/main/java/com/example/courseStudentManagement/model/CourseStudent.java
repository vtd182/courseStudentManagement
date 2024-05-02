package com.example.courseStudentManagement.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Course_Student")
public class CourseStudent {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Student_Id", referencedColumnName = "Id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "Course_Id", referencedColumnName = "Id")
    private Course course;

    @Column(name = "Grade")
    private float grade;

    public CourseStudent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
