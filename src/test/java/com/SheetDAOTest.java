package com;

import com.dao.SheetDAO;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

public class SheetDAOTest extends AbstractTest {
    private static SheetDAO sheetDAO = SheetDAO.getInstance();

    {
        commonDAO = sheetDAO;
    }

    @Test()
    public void insertSheet() {
        //GIVEN
        int expectedSize = 2;
        //WHEN
        Connection conn = commonDAO.getConnection();
        sheetDAO.insertSheet(conn);
        int actualSize = 0;
        try {
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM sheet").executeQuery();
            rs.next();
            actualSize = rs.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }
}
