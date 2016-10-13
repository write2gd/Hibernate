package com.gd.demo.hql;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author Gouranga Das
 */
public class Student {

    private long id;

    private String studentName;

    private Set<Course> courses = new HashSet<Course>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }
}
