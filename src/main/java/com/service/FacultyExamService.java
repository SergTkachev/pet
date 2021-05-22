package com.service;

import com.dao.FacultyExamDAO;
import com.model.FacultyExam;
import com.validation.FacultyExamValidator;

import java.util.List;

public class FacultyExamService {
    private static FacultyExamService instance;
    private FacultyExamDAO facultyExamDAO;
    private FacultyExamValidator facultyExamValidator;

    public FacultyExamService() {
        facultyExamDAO = FacultyExamDAO.getInstance();
        facultyExamValidator = FacultyExamValidator.getInstance();
    }

    public static synchronized FacultyExamService getInstance() {
        if (instance == null) {
            instance = new FacultyExamService();
        }
        return instance;
    }

    public void add(FacultyExam facultyExam) {
        facultyExamValidator.isNameNullOrEmpty(facultyExam.getExam());
        facultyExamValidator.isFacultyExamNameUnique(facultyExam);
        facultyExamDAO.add(facultyExam);
    }

    public void updateList(List<FacultyExam> facultyExams, String facultyId) {
        for (FacultyExam facultyExam : facultyExams) {
            facultyExamValidator.isNameNullOrEmpty(facultyExam.getExam());
        }
        facultyExamValidator.isFacultyExamNamesUnique(facultyExams, facultyId);
        facultyExamDAO.updateList(facultyExams);
    }

    public void delete(String id) {
        facultyExamValidator.isIdValid(id);
        facultyExamValidator.isExist(id);
        facultyExamDAO.delete(id);
    }
}