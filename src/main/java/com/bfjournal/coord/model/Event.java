package com.bfjournal.coord.model;

import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String[] contacts;

    public Event(Integer id, String name, String... contacts) {
        super();
        this.id = id;
        this.name = name;
        this.contacts = contacts.clone();
    }

    public Event(String name, String... contacts) {
        this(null, name, contacts);
    }

    @SuppressWarnings("unused")
    public Event() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getContacts() {
        return contacts;
    }

    public void setContacts(String[] contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + "]";
    }

}