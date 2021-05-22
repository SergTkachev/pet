package com;

import com.dao.FacultyExamDAO;
import com.model.FacultyExam;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyExamDAOTest extends AbstractTest {
    private static FacultyExamDAO facultyExamDAO = FacultyExamDAO.getInstance();

    {
        commonDAO = facultyExamDAO;
    }

    @Test()
    public void getAllByFacultyId() {
        //GIVEN
        int expectedSize = 2;
        //WHEN
        List<FacultyExam> list = facultyExamDAO.getAllByFacultyId("1");
        int actualSize = list.size();
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test()
    public void get() {
        //GIVEN
        String expectedFirstName0 = "FirstExam1";
        //WHEN
        String actualFirstName0 = facultyExamDAO.get("1").getExam();
        //THEN
        Assert.assertNotNull(actualFirstName0);
        Assert.assertEquals(expectedFirstName0, actualFirstName0);
    }

    @Test()
    public void add() {
        //GIVEN
        int expectedSize = 3;
        //WHEN
        FacultyExam facultyExam = new FacultyExam("-1", "1", "oncemore");
        facultyExamDAO.add(facultyExam);
        int actualSize = facultyExamDAO.getAllByFacultyId("1").size();
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test()
    public void updateList() {
        //GIVEN
        String expectedName0 = "changed1";
        String expectedName2 = "changed2";
        //WHEN
        FacultyExam faculty0 = facultyExamDAO.get("1");
        faculty0.setExam(expectedName0);
        FacultyExam faculty2 = facultyExamDAO.get("3");
        faculty2.setExam(expectedName2);
        List<FacultyExam> exams = new ArrayList<>();
        exams.add(faculty0);
        exams.add(faculty2);
        facultyExamDAO.updateList(exams);
        String actualName0 = facultyExamDAO.get("1").getExam();
        String actualName2 = facultyExamDAO.get("3").getExam();
        //THEN
        Assert.assertNotNull(actualName0);
        Assert.assertNotNull(actualName2);
        Assert.assertEquals(expectedName0, actualName0);
        Assert.assertEquals(expectedName2, actualName2);
    }

    @Test()
    public void delete() {
        //GIVEN
        int expectedSize = 1;
        //WHEN
        facultyExamDAO.delete("2");
        int actualSize = facultyExamDAO.getAllByFacultyId("1").size();
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test()
    public void facultyExamNames() {
        //GIVEN
        int expectedSize = 2;
        String expectedName0 = "FirstExam1";
        //WHEN
        Map<Integer, String> names = new HashMap<>();
        facultyExamDAO.facultyExamNames(names, "1");
        int actualSize = names.size();
        String actualName0 = names.get(1);
        //THEN
        Assert.assertNotNull(actualName0);
        Assert.assertEquals(expectedSize, actualSize);
        Assert.assertEquals(expectedName0, actualName0);
    }
}
