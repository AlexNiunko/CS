package com.epam.cs.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class User extends AbstractEntity {
    private String name;
    private String surName;
    private String dateOfExpirity;
    private String drivingLicenseNumber;
    private String mail;

    private String pass;
    private int role;
    private int accessLevel;

    public User(String name, String surName, String dateOfExpirity, String identificationNumber, String mail, String pass) {
        this.name = name;
        this.surName = surName;
        this.dateOfExpirity = dateOfExpirity;
        this.drivingLicenseNumber = identificationNumber;
        this.mail = mail;
        this.pass = pass;
        this.role = Role.CUSTOMER.ordinal();
        this.accessLevel=AccessLevel.FREE.ordinal();
    }

    public User() {
        this.role = Role.CUSTOMER.ordinal();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public int getRole() {
        return role;
    }

    public String getDateOfExpirity() {
        return dateOfExpirity;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setDateOfExpirity(String dateOfExpirity) {
        this.dateOfExpirity = dateOfExpirity;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return role == user.role && accessLevel == user.accessLevel && Objects.equals(name, user.name) && Objects.equals(surName, user.surName) && Objects.equals(dateOfExpirity, user.dateOfExpirity) && Objects.equals(drivingLicenseNumber, user.drivingLicenseNumber) && Objects.equals(mail, user.mail) && Objects.equals(pass, user.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surName, dateOfExpirity, drivingLicenseNumber, mail, pass, role, accessLevel);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surName='" + surName + "'")
                .add("dateOfExpirity='" + dateOfExpirity + "'")
                .add("drivingLicenseNumber='" + drivingLicenseNumber + "'")
                .add("mail='" + mail + "'")
                .add("pass='" + pass + "'")
                .add("role=" + role)
                .add("accessLevel=" + accessLevel)
                .toString();
    }
}
