package com.bfjournal.coord.model;

import java.io.Serializable;

/**
 * Created by grant on 8/19/15.
 */
public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;

    private String number;

    @SuppressWarnings("unused")
    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        return !(number != null ? !number.equals(phone.number) : phone.number != null);

    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}
