package com;

import com.dao.FacultyDAO;
import com.model.Faculty;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyDAOTest extends AbstractTest {
    private static FacultyDAO facultyDAO = FacultyDAO.getInstance();

    {
        commonDAO = facultyDAO;
    }

    @Test()
    public void getAll() {
        //GIVEN
        int expectedSize = 5;
        String expectedName0 = "ThirdFaculty";
        //WHEN
        List<Faculty> list = facultyDAO.getAll("name", "DESC");
        int actualSize = list.size();
        String actualName0 = list.get(0).getName();
        //THEN
        Assert.assertNotNull(actualName0);
        Assert.assertEquals(expectedSize, actualSize);
        Assert.assertEquals(expectedName0, actualName0);
    }

    @Test()
    public void get() {
        //GIVEN
        String expectedName0 = "FourthFaculty";
        //WHEN
        String actualName0 = facultyDAO.get("4").getName();
        //THEN
        Assert.assertNotNull(actualName0);
        Assert.assertEquals(expectedName0, actualName0);
    }

    @Test()
    public void add() {
        //GIVEN
        int expectedSize = 6;
        //WHEN
        Faculty faculty = new Faculty();
        faculty.setName("something");
        faculty.setBudget("10");
        faculty.setCommon("20");
        facultyDAO.add(faculty);
        int actualSize = facultyDAO.getAll("name", "DESC").size();
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test()
    public void updateList() {
        //GIVEN
        String expectedName1 = "changed1";
        String expectedName3 = "changed2";
        //WHEN
        Faculty faculty1 = facultyDAO.get("1");
        faculty1.setName("changed1");
        Faculty faculty3 = facultyDAO.get("3");
        faculty3.setName("changed2");
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty1);
        faculties.add(faculty3);
        facultyDAO.updateList(faculties);
        String actualName1 = facultyDAO.get("1").getName();
        String actualName3 = facultyDAO.get("3").getName();
        //THEN
        Assert.assertNotNull(actualName1);
        Assert.assertNotNull(actualName3);
        Assert.assertEquals(expectedName1, actualName1);
        Assert.assertEquals(expectedName3, actualName3);
    }

    @Test()
    public void delete() {
        //GIVEN
        int expectedSize = 4;
        //WHEN
        facultyDAO.delete("2");
        int actualSize = facultyDAO.getAll("name", "DESC").size();
        Faculty faculty = facultyDAO.get("2");
        //THEN
        Assert.assertNull(faculty);
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test()
    public void facultyNames() {
        //GIVEN
        int expectedSize = 5;
        String expectedName0 = "FirstFaculty";
        //WHEN
        Map<Integer, String> names = new HashMap<>();
        facultyDAO.facultyNames(names);
        int actualSize = names.size();
        String actualName0 = names.get(1);
        //THEN
        Assert.assertNotNull(actualName0);
        Assert.assertEquals(expectedSize, actualSize);
        Assert.assertEquals(expectedName0, actualName0);
    }

    @Test()
    public void getRegistrations() {
        //GIVEN
        int expectedSize = 3;
        String expectedName0 = "ThirdFaculty";
        //WHEN
        List<Faculty> list = facultyDAO.getRegistrations("2", "name", "DESC");
        int actualSize = list.size();
        String actualName0 = list.get(0).getName();
        //THEN
        Assert.assertNotNull(actualName0);
        Assert.assertEquals(expectedSize, actualSize);
        Assert.assertEquals(expectedName0, actualName0);
    }
}
