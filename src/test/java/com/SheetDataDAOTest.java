package com;

import com.dao.SheetDataDAO;
import com.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

public class SheetDataDAOTest extends AbstractTest {
    private static SheetDataDAO sheetDataDAO = SheetDataDAO.getInstance();

    {
        commonDAO = sheetDataDAO;
    }

    @Test()
    public void insertSheetData() {
        //GIVEN
        int expectedSize = 28;
        //WHEN
        Connection conn = commonDAO.getConnection();
        sheetDataDAO.insertSheetData(conn, 1);
        int actualSize = 0;
        try {
            ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM sheet_data").executeQuery();
            rs.next();
            actualSize = rs.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //THEN
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test()
    public void getLastSheet() {
        //GIVEN
        int expectedBudgetFacultySize = 2;
        int expectedContractFacultySize = 2;
        String expectedBudgetFacultyName0 = "FirstFaculty";
        String expectedBudgetFacultyName1 = "SecondFaculty";
        String expectedContractFacultyName0 = "FirstFaculty";
        String expectedContractFacultyName1 = "SecondFaculty";
        int expectedBudgetSize = 1;
        int expectedContractSize = 2;
        String expectedBudgetFaculty0Email0 = "fourth@fourth.com";
        String expectedContractFaculty0Email0 = "sixth@sixth.com";
        String expectedContractFaculty0Email1 = "third@third.com";
        //WHEN
        Connection conn = commonDAO.getConnection();
        NavigableMap<String, NavigableSet<User>> budgetMap = new TreeMap<>();
        NavigableMap<String, NavigableSet<User>> contractMap = new TreeMap<>();
        sheetDataDAO.getLastSheet(conn, budgetMap, contractMap);
        int actualBudgetFacultySize = budgetMap.size();
        int actualContractFacultySize = contractMap.size();
        String actualBudgetFacultyName0 = budgetMap.firstKey();
        String actualBudgetFacultyName1 = budgetMap.lastKey();
        String actualContractFacultyName0 = contractMap.firstKey();
        String actualContractFacultyName1 = contractMap.lastKey();
        int actualBudgetSize = budgetMap.firstEntry().getValue().size();
        int actualContractSize = contractMap.firstEntry().getValue().size();
        String actualBudgetFaculty0Email0 = budgetMap.firstEntry().getValue().first().getEmail();
        String actualContractFaculty0Email0 = contractMap.firstEntry().getValue().first().getEmail();
        String actualContractFaculty0Email1 = contractMap.firstEntry().getValue().last().getEmail();
        //THEN
        Assert.assertNotNull(expectedBudgetFacultyName0);
        Assert.assertNotNull(expectedBudgetFacultyName1);
        Assert.assertNotNull(expectedContractFacultyName0);
        Assert.assertNotNull(expectedContractFacultyName1);
        Assert.assertNotNull(expectedBudgetFaculty0Email0);
        Assert.assertNotNull(expectedContractFaculty0Email0);
        Assert.assertNotNull(expectedContractFaculty0Email1);
        Assert.assertEquals(expectedBudgetFacultySize, actualBudgetFacultySize);
        Assert.assertEquals(expectedContractFacultySize, actualContractFacultySize);
        Assert.assertEquals(expectedBudgetFacultyName0, actualBudgetFacultyName0);
        Assert.assertEquals(expectedBudgetFacultyName1, actualBudgetFacultyName1);
        Assert.assertEquals(expectedContractFacultyName0, actualContractFacultyName0);
        Assert.assertEquals(expectedContractFacultyName1, actualContractFacultyName1);
        Assert.assertEquals(expectedBudgetSize, actualBudgetSize);
        Assert.assertEquals(expectedContractSize, actualContractSize);
        Assert.assertEquals(expectedBudgetFaculty0Email0, actualBudgetFaculty0Email0);
        Assert.assertEquals(expectedContractFaculty0Email0, actualContractFaculty0Email0);
        Assert.assertEquals(expectedContractFaculty0Email1, actualContractFaculty0Email1);
    }
}
