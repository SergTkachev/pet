package com.validation;

import com.dao.FacultyDAO;
import com.model.Faculty;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.ParamException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyValidator extends Validator {
    private static FacultyValidator instance;
    private FacultyDAO facultyDAO;

    private FacultyValidator() {
        facultyDAO = FacultyDAO.getInstance();
    }

    public static synchronized FacultyValidator getInstance() {
        if (instance == null) {
            instance = new FacultyValidator();
        }
        return instance;
    }

    public void isIdValid(String idString) {
        if (isIntegerValid(idString)) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_id")), "ID", "1");
        }
    }

    public void isNameNullOrEmpty(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("empty_name")), "Name", "");
        }
    }

    public void isBudgetValid(String budgetString) {
        if (isIntegerValid(budgetString)) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_budget")), "Budget", "1");
        }
    }

    public void isCommonValid(String commonString) {
        if (isIntegerValid(commonString)) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_common")), "Common", "1");
        }
    }

    public void isBudgetCommonValid(String budgetString, String commonString) {
        boolean exception = false;
        if (Integer.parseInt(commonString) < Integer.parseInt(budgetString)) {
            exception = true;
        }
        if (exception) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("budget_cannot_be_more_than_common")), "Budget/Common", "1");
        }
    }

    public void isExist(String id) {
        if (facultyDAO.get(id) == null) {
            throw new NotFoundException(loc.getString("faculty_is_absent"));
        }
    }

    public void isFacultyNameUnique(Faculty faculty) {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        isFacultyNamesUnique(faculties);
    }

    public void isFacultyNamesUnique(List<Faculty> faculties) {
        Map<Integer, String> names = new HashMap<>();
        facultyDAO.facultyNames(names);
        for (Faculty faculty : faculties) {
            int id = Integer.parseInt(faculty.getId());
            String name = faculty.getName();
            if (names.values().contains(name) && !name.equals(names.get(id))) {
                throw new ParamException.FormParamException(new RuntimeException(loc.getString("non_unique_faculty_name")), "Name", "");
            } else {
                names.put(id, name);
            }
        }
    }
}
