package com.gd.demo.manytomanybidirectional.annotation;


import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Gouranga Das
 */
public class ManyToManyBidirectionalDemo {

    /**
     * @param args accepts variable arguments
     */
    public static void main(String[] args) {
        //Persisting Student and Course objects
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student student1 = new Student();
        student1.setStudentName("Prathap Kumar");

        Student student2 = new Student();
        student2.setStudentName("Gouranga Das");

        Course course1 = new Course();
        course1.setCourseName("English");

        Course course2 = new Course();
        course2.setCourseName("Computer");

        student1.addCourse(course1);
        student1.addCourse(course2);
        student2.addCourse(course1);
        student2.addCourse(course2);

        session.save(student1);
        session.save(student2);
        tx.commit();
        session.close();

        //Loading the Student and the Course objects
        Session sessionTwo = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction txTwo = sessionTwo.beginTransaction();
        List studentList = sessionTwo.createQuery(" from Student ").list(); //as we use the Student class in different packages we mentioned the packeage name here
        System.out.println("*****************************************");
        System.out.println("*****************************************");
        for (Object stud : studentList) {
            System.out.println("Student Name is " + ((Student) stud).getStudentName());
            Set courses = ((Student) stud).getCourses();
            System.out.println("Courses to which this student is associated");
            for (Object course : courses) {
                System.out.println(((Course) course).getCourseName());
            }
        }
        System.out.println("*****************************************");
        System.out.println("*****************************************");
        txTwo.commit();
        sessionTwo.close();

        HibernateAnnotationUtil.shutdown();
        System.out.println("*******Done*******");

    }

}
