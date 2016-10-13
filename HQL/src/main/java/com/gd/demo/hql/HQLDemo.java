package com.gd.demo.hql;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Gouranga Das This class will give some samples on HQL HQL is
 * case-insensitive
 */
public class HQLDemo {

    /**
     * @param args accepts variable arguments
     */
    public static void main(String[] args) {
        //Persist the Student and Course objects
        persistStudent();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        //Example for HQL from clause
        System.out.println("************** Example of from clause ******************");
        List studentList = session.createQuery(" from Student ").list();
        for (Object stud : studentList) {
            System.out.println("Student Name is " + ((Student) stud).getStudentName());
            Set courses = ((Student) stud).getCourses();
            System.out.println("Courses to which this student is associated");
            for (Object course : courses) {
                System.out.println(((Course) course).getCourseName());
            }
        }

        //Example for joins -- hibernate supports    inner join, left outer join, right outer join, full join
        System.out.println("********************** Example of join *****************");
        List joinList = session.createQuery("from Student as s inner join s.courses").list();
        Iterator ite = joinList.iterator();
        while (ite.hasNext()) {
            Object[] objects = (Object[]) ite.next();
            Student student = (Student) objects[0];
            Course course = (Course) objects[1];
            System.out.println("Student Name " + student.getStudentName());
            System.out.println("Course Name " + course.getCourseName());
        }

        //Example of Select clause -- The select clause picks which objects and properties to return in the query result set. 
        System.out.println("********************** Example of Select Clause *******************");
        List selectList = session.createQuery("select distinct s from Student as s inner join s.courses").list(); //now only Student object is returned and not the Course object
        for (Object sl : selectList) {
            System.out.println("Student Name " + ((Student) sl).getStudentName());
        }

        //Example of Aggregate function
        System.out.println("********************* Example of Aggregate function *****************");
        List aggreList = session.createQuery("select count(distinct s.studentName) from Student as s inner join s.courses").list();
        System.out.println("Total Number of Students " + aggreList.toString());

        //Example of Where clause
        System.out.println("***************Example of Where clause ******************");
        List whereList = session.createQuery("select distinct s from Student as s inner join s.courses where s.studentName= :sname").setParameter("sname", "Prathap Kumar").list();//The where clause allows you to refine the list of instances returned.
        for (Object wl : whereList) {
            System.out.println("Student Id is " + ((Student) wl).getId());
        }

        //Example of Order by clause
        System.out.println("************* Example of Order by clause ******************");
        List orderList = session.createQuery("select s from Student s order by s.studentName asc").list();
        for (Object ol : orderList) {
            System.out.println("Student name in ascending order " + ((Student) ol).getStudentName());
        }

        //Example of Group By clause
        System.out.println("***********Example of Group by clause *****************");
        List groupList = session.createQuery("select c.courseName, count(distinct c.courseName) from Student as s, Course c inner join s.courses group by c.courseName").list();
        //A query that returns aggregate values can be grouped by any property of a returned class or components
        ite = groupList.iterator();
        while (ite.hasNext()) {
            Object[] objects = (Object[]) ite.next();
            System.out.println("Course Name is " + objects[0]);
            System.out.println("Count representation of the above course " + objects[1]);
        }
        //Example of Subqueries
        System.out.println("******************Example of SubQueries *********************");
        List subList = session.createQuery("select s from Student s where s.id not in (select distinct s from Student as s inner join s.courses where s.studentName= 'Prathap Kumar')").list();
        System.out.println("List size " + subList.size());
        for (Object sl : subList) {
            System.out.println("Student Name from the sub query is " + ((Student) sl).getStudentName());
        }
        //Example of native sql
        System.out.println("******************* Hibernate with native sql queries **********************");
        List nativeSqlList = session.createSQLQuery("select * from STUDENTS").list(); // STEDENTS refer the actual table name
        ite = nativeSqlList.iterator();
        while (ite.hasNext()) {
            Object[] objects = (Object[]) ite.next();
            System.out.println("Student Id " + objects[0]);
            System.out.println("Student name " + objects[1]);
        }

        //Example of native sql with automatic resultset handling
        System.out.println("****************Native SQL with Auto Conversion of ResultSet to Entity*********************");
        List nativeRsHandlinglList = session.createSQLQuery("select * from COURSES").addEntity(Course.class).list(); // COURSES refer the actual table name
        for (Object nativeList : nativeRsHandlinglList) {
            System.out.println("Course name is  " + ((Course) nativeList).getCourseName());
        }

        //Example of named hql
        System.out.println("*******************Example of named HQL query ******************");
        List namedHqlList = session.getNamedQuery("namedStudentHql").setString("sname", "Joseph Raj Kumar").list();
        for (Object namedhql : whereList) {
            System.out.println("Student Id is " + ((Student) namedhql).getId());
        }

        //Eample of named SQL 
        System.out.println("****************Example of named SQL query *********************");
        List namedSqlList = session.getNamedQuery("namedNativeStudentSQL").list();
        ite = nativeSqlList.iterator();
        while (ite.hasNext()) {
            Object[] objects = (Object[]) ite.next();
            System.out.println("Student Id " + objects[0]);
            System.out.println("Student name " + objects[1]);
        }

        System.out.println("-------------------End of HQL Demo------------------");
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    private static void persistStudent() {
        //Persisting Student and Course objects
        Session session = HibernateUtil.getSessionFactory().openSession();
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
        //session.close();
        //HibernateUtil.shutdown();
    }
}
