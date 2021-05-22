package com.model;

import java.util.ArrayList;
import java.util.List;

public class User implements Comparable<User> {
    private String id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String district;
    private String city;
    private String school;
    private double averageMark;
    private boolean blocked;
    private boolean admin;
    private boolean withScan;
    private List<Mark> marks = new ArrayList<>();

    public User(String id, String email, String firstName, String middleName, String lastName, String district, String city, String school, double averageMark, boolean blocked, boolean admin) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.district = district;
        this.city = city;
        this.school = school;
        this.averageMark = averageMark;
        this.blocked = blocked;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isWithScan() {
        return withScan;
    }

    public void setWithScan(boolean withScan) {
        this.withScan = withScan;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public int compareTo(User o) {
        int result = lastName.compareTo(o.lastName);
        if (result == 0) {
            result = firstName.compareTo(o.firstName);
            if (result == 0) {
                result = middleName.compareTo(o.middleName);
            }
        }
        return result;
    }
}
