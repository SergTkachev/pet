package com;

import com.dao.RegistrationDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

public class RegistrationDAOTest extends AbstractTest {
    private static RegistrationDAO registrationDAO = RegistrationDAO.getInstance();

    {
        commonDAO = registrationDAO;
    }

    @Test()
    public void insertRegistration() {
        //GIVEN
        int expectedSize = 8;
        //WHEN
        Connection conn = commonDAO.getConnection();
        registrationDAO.insertRegistration(conn, "6", "5");
        int actualSize = 0;
        try {
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM registration").executeQuery();
            rs.next();
            actualSize = rs.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test()
    public void deleteRegistrations() {
        //GIVEN
        int expectedSize = 0;
        //WHEN
        Connection conn = commonDAO.getConnection();
        registrationDAO.deleteRegistrations(conn);
        int actualSize = 0;
        try {
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM registration").executeQuery();
            rs.next();
            actualSize = rs.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }
}
