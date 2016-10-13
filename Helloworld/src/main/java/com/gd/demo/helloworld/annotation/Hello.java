package com.gd.demo.helloworld.annotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * This is a HelloWorld example of Hibernate
 *
 * @author Gouranga Das
 */
@Entity
@Table(name = "Hello")
public class Hello {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HELLO_ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MESSAGE")
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
