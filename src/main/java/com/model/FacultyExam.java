package com.model;

public class FacultyExam {
    private String id;
    private String facultyId;
    private String exam;
    private String exammark = "";

    public FacultyExam() {
    }

    public FacultyExam(String id, String facultyId, String exam) {
        this.id = id;
        this.facultyId = facultyId;
        this.exam = exam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getExammark() {
        return exammark;
    }

    public void setExammark(String exammark) {
        this.exammark = exammark;
    }
}
