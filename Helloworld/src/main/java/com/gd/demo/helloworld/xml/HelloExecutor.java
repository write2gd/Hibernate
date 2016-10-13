package com.gd.demo.helloworld.xml;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * This is a HelloWorld example of Hibernate
 *
 * @author Gouranga Das
 */
public class HelloExecutor {

    /**
     * @param args accepts variable arguments
     */
    public static void main(String[] args) {

        //Persisting the hello object
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Hello helloFirstObj = new Hello();
        helloFirstObj.setName("Gouranga");
        helloFirstObj.setMessage("Welcome");

        Hello helloSecondObj = new Hello();
        helloSecondObj.setName("Raja");
        helloSecondObj.setMessage("Congrates");

        session.save(helloFirstObj);
        session.save(helloSecondObj);
        tx.commit();
        session.close();

        //Retrieving all the hello objects
        Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
        Transaction txTwo = sessionTwo.beginTransaction();

        List helloList = sessionTwo.createQuery("from com.gd.demo.helloworld.xml.Hello ").list();   // Hello is the name of the ORM class and lower case  hello should not be used
        System.out.println("**********************************************");
        System.out.println("**********************************************");
        for (Object helloObjs : helloList) {
            System.out.println("The name is " + ((Hello) helloObjs).getName());
            System.out.println("The message is " + ((Hello) helloObjs).getMessage());
        }
        System.out.println("**********************************************");
        System.out.println("**********************************************");

        txTwo.commit();
        sessionTwo.close();

        // Retrieving only the required object or row
        Session sessionThree = HibernateUtil.getSessionFactory().openSession();
        Transaction txThree = sessionThree.beginTransaction();

        Hello hello = (Hello) sessionThree.load(Hello.class, new Long(2));  // row with pk id 2 is retrieved
        System.out.println("**********************************************");
        System.out.println("**********************************************");
        System.out.println("The name from the second retrieved object " + hello.getName());
        System.out.println("The message from the second retrieved object " + hello.getMessage());
        System.out.println("**********************************************");
        System.out.println("**********************************************");

        txThree.commit();
        sessionThree.close();

        HibernateUtil.shutdown();
        System.out.println("--------Done-------");
    }
}
