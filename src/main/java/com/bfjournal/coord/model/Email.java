package com.bfjournal.coord.model;

import java.io.Serializable;

/**
 * Created by grant on 8/19/15.
 */
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;

    private String address;

    public Email() {
    }

    public Email(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return !(address != null ? !address.equals(email.address) : email.address != null);

    }

    @Override
    public int hashCode() {
        return address != null ? address.hashCode() : 0;
    }
}
