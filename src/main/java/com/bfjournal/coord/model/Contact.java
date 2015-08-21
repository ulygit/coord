package com.bfjournal.coord.model;

import java.io.Serializable;

/**
 * Created by grant on 8/18/15.
 */
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private Email email;
    private Phone phone;

    @SuppressWarnings("unused")
    public Contact() {
    }

    public Contact(Phone phone) {
        this.phone = phone;
    }

    public Contact(Email email) {
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "email=" + email +
                ", phone=" + phone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return !(email != null ? !email.equals(contact.email) : contact.email != null);

    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
