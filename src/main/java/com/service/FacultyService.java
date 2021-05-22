package com.service;

import com.dao.FacultyDAO;
import com.dao.FacultyExamDAO;
import com.model.Faculty;
import com.validation.FacultyValidator;

import java.util.ArrayList;
import java.util.List;

public class FacultyService {
    private static FacultyService instance;
    private FacultyDAO facultyDAO;
    private FacultyExamDAO facultyExamDAO;
    private FacultyValidator facultyValidator;

    public FacultyService() {
        facultyDAO = FacultyDAO.getInstance();
        facultyExamDAO = FacultyExamDAO.getInstance();
        facultyValidator = FacultyValidator.getInstance();
    }

    public static synchronized FacultyService getInstance() {
        if (instance == null) {
            instance = new FacultyService();
        }
        return instance;
    }

    public List<Faculty> getAll(String column, String direction) {
        if (column == null) {
            column = "name";
        }
        if (direction == null) {
            direction = "ASC";
        }
        return facultyDAO.getAll(column, direction);
    }

    public List<Faculty> getRegistrations(String userId, String column, String direction) {
        if (column == null) {
            column = "name";
        }
        if (direction == null) {
            direction = "ASC";
        }
        return facultyDAO.getRegistrations(userId, column, direction);
    }

    public Faculty get(String id) {
        facultyValidator.isIdValid(id);
        facultyValidator.isExist(id);
        Faculty faculty = facultyDAO.get(id);
        faculty.setExams(facultyExamDAO.getAllByFacultyId(id));
        return faculty;
    }

    public void add(Faculty faculty) {
        facultyValidator.isNameNullOrEmpty(faculty.getName());
        facultyValidator.isBudgetValid(faculty.getBudget());
        facultyValidator.isCommonValid(faculty.getCommon());
        facultyValidator.isBudgetCommonValid(faculty.getBudget(), faculty.getCommon());
        facultyValidator.isFacultyNameUnique(faculty);
        facultyDAO.add(faculty);
    }

    public void updateList(List<Faculty> faculties) {
        for (Faculty faculty : faculties) {
            facultyValidator.isNameNullOrEmpty(faculty.getName());
            facultyValidator.isBudgetValid(faculty.getBudget());
            facultyValidator.isCommonValid(faculty.getCommon());
            facultyValidator.isBudgetCommonValid(faculty.getBudget(), faculty.getCommon());
            facultyValidator.isExist(faculty.getId());
        }
        facultyValidator.isFacultyNamesUnique(faculties);
        facultyDAO.updateList(faculties);
    }

    public void update(Faculty faculty) {
        List<Faculty> faculties = new ArrayList();
        faculties.add(faculty);
        updateList(faculties);
    }

    public void delete(String id) {
        facultyValidator.isIdValid(id);
        facultyValidator.isExist(id);
        facultyDAO.delete(id);
    }
}