package com.service;

import com.dao.SheetDAO;
import com.model.User;

import java.util.NavigableMap;
import java.util.NavigableSet;


public class SheetService {
    private static SheetService instance;
    private SheetDAO sheetDAO;

    private SheetService() {
        sheetDAO = SheetDAO.getInstance();
    }

    public static synchronized SheetService getInstance() {
        if (instance == null) {
            instance = new SheetService();
        }
        return instance;
    }

    public void createSheet(NavigableMap<String, NavigableSet<User>> budgetMap,
                            NavigableMap<String, NavigableSet<User>> contractMap) {
        sheetDAO.createSheet(budgetMap, contractMap);
    }
}
