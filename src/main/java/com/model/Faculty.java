package com.model;

import java.util.List;

public class Faculty {
    private String id;
    private String name;
    private String budget;
    private String common;
    private List<FacultyExam> exams;

    public Faculty() {
    }

    public Faculty(String id, String name, String budget, String common) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.common = common;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public List<FacultyExam> getExams() {
        return exams;
    }

    public void setExams(List<FacultyExam> exams) {
        this.exams = exams;
    }
}
