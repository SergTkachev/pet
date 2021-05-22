package com;

import com.dao.UserDAO;
import com.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserDAOTest extends AbstractTest {
    private static UserDAO userDAO = UserDAO.getInstance();

    {
        commonDAO = userDAO;
    }

    @Test
    public void authenticate() {
        //GIVEN
        //WHEN
        boolean actual = userDAO.authenticate("admin@admin.com", "admin");
        //THEN
        Assert.assertTrue(actual);
    }

    @Test
    public void indentificate() {
        //GIVEN
        String expectedFirstName = "admin";
        //WHEN
        String actualFirstName = userDAO.indentificate("admin@admin.com").getFirstName();
        //THEN
        Assert.assertEquals(expectedFirstName, actualFirstName);
    }

    @Test
    public void getAll() {
        //GIVEN
        int expectedSize = 5;
        String expectedName0 = "fifth";
        //WHEN
        List<User> list = userDAO.getAll("");
        int actualSize = list.size();
        String actualName0 = list.get(0).getFirstName();
        //THEN
        Assert.assertNotNull(actualName0);
        Assert.assertEquals(expectedSize, actualSize);
        Assert.assertEquals(expectedName0, actualName0);
    }

    @Test
    public void userExists() {
        //GIVEN
        //WHEN
        boolean actual = userDAO.userExists("admin@admin.com");
        //THEN
        Assert.assertTrue(actual);
    }

    @Test
    public void get() {
        //GIVEN
        String expectedFirstName0 = "fourth";
        //WHEN
        String actualFirstName0 = userDAO.get("4").getFirstName();
        //THEN
        Assert.assertNotNull(actualFirstName0);
        Assert.assertEquals(expectedFirstName0, actualFirstName0);
    }

    @Test
    public void insertStudent() {
        //GIVEN
        int expectedSize = 6;
        //WHEN
        User student = new User("-1",
                "something@email.com",
                "something",
                "",
                "else",
                "",
                "",
                "",
                11,
                false,
                false);
        userDAO.insertStudent(commonDAO.getConnection(), student, "passwd");
        int actualSize = userDAO.getAll("").size();
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void blockUnblockStudent() {
        //GIVEN
        //WHEN
        userDAO.blockUnblockStudent("2");
        boolean actual = userDAO.get("2").isBlocked();
        //THEN
        Assert.assertTrue(actual);
    }

    @Test
    public void initialAdminPasswordSetting() {
        //GIVEN
        //WHEN
        userDAO.initialAdminPasswordSetting("another");
        boolean actual = userDAO.authenticate("admin@admin.com", "another");
        //THEN
        Assert.assertTrue(actual);
    }
}
