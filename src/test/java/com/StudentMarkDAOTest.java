package com;

import com.dao.StudentMarkDAO;
import com.model.Mark;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentMarkDAOTest extends AbstractTest {
    private static StudentMarkDAO studentMarkDAO = StudentMarkDAO.getInstance();

    {
        commonDAO = studentMarkDAO;
    }

    @Test()
    public void insertStudentMarks() {
        //GIVEN
        int expectedSize = 12;
        //WHEN
        List<Mark> marks = new ArrayList<>();
        marks.add(new Mark("-1", "6", "subj1", "5"));
        marks.add(new Mark("-1", "6", "subj1", "10"));
        Connection conn = commonDAO.getConnection();
        studentMarkDAO.insertStudentMarks(conn, "6", marks);
        int actualSize = 0;
        try {
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM student_mark").executeQuery();
            rs.next();
            actualSize = rs.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }
}
