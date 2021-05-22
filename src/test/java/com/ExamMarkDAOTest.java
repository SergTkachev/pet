package com;

import com.dao.ExamMarkDAO;
import com.model.Faculty;
import com.model.FacultyExam;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExamMarkDAOTest extends AbstractTest {
    private static ExamMarkDAO examMarkDAO = ExamMarkDAO.getInstance();

    {
        commonDAO = examMarkDAO;
    }

    @Test()
    public void insertStudentMarks() {
        //GIVEN
        int expectedSize = 16;
        //WHEN
        FacultyExam exam0 = new FacultyExam("5", "", "");
        exam0.setExammark("5");
        FacultyExam exam1 = new FacultyExam("6", "", "");
        exam1.setExammark("6");
        List<FacultyExam> exams = new ArrayList<>();
        exams.add(exam0);
        exams.add(exam1);
        Faculty faculty = new Faculty("", "", "", "");
        faculty.setExams(exams);
        Connection conn = commonDAO.getConnection();
        examMarkDAO.insertExamMarks(conn, 7, faculty);
        int actualSize = 0;
        try {
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM exam_mark").executeQuery();
            rs.next();
            actualSize = rs.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }
}
