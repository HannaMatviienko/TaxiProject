package com.example.taxi.model.entity;

import java.util.Objects;

public class User {

    public User() {
        id = -1;
        role = ROLE.GUEST;
        firstName = "";
        lastName = "";
        email = "";
        password = "";
    }

    public User(long id, ROLE role) {
        this.setId(id);
        this.setRole(role);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        final User other = (User) obj;

        if (!Objects.equals(this.id, other.id))
            return false;
        if (!Objects.equals(this.role, other.role))
            return false;

        if (!Objects.equals(this.firstName, other.firstName))
            return false;
        if (!Objects.equals(this.lastName, other.lastName))
            return false;
        return Objects.equals(this.email, other.email);
    }


    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public enum ROLE {
        USER, ADMIN, GUEST;

        public static ROLE parseRole(String value)
        {
            switch (value)
            {
                case "ROLE_ADMIN":
                    return  ADMIN;
                case "ROLE_USER":
                    return  USER;
                default:
                    return GUEST;
            }
        }

        public String toString()
        {
            switch (this)
            {
                case USER:
                    return "ROLE_USER";
                case ADMIN:
                    return "ROLE_ADMIN";
                default:
                    return "ROLE_GUEST";
            }
        }
    }

    private ROLE role;

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public String getScreenName()
    {
        return getFirstName() + " " + getLastName();
    }
}