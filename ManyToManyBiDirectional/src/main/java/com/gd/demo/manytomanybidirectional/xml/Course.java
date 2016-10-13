package com.gd.demo.manytomanybidirectional.xml;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author Gouranga Das
 */
public class Course {

    private long id;

    private String courseName;

    private Set<Student> students = new HashSet<Student>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
