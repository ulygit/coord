package com.bfjournal.coord.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private Integer id;

    @XmlElement
    private String name;

    public Event(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + "]";
    }
}