package com.model;

public class Mark {
    private String id;
    private String studentId;
    private String subject;
    private String mark;

    public Mark(String id, String studentId, String subject, String mark) {
        this.id = id;
        this.studentId = studentId;
        this.subject = subject;
        this.mark = mark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
