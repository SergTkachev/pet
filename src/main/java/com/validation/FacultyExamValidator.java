package com.validation;

import com.dao.FacultyExamDAO;
import com.model.FacultyExam;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.ParamException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyExamValidator extends Validator {
    private static FacultyExamValidator instance;
    private FacultyExamDAO facultyExamDAO;

    private FacultyExamValidator() {
        facultyExamDAO = FacultyExamDAO.getInstance();
    }

    public static synchronized FacultyExamValidator getInstance() {
        if (instance == null) {
            instance = new FacultyExamValidator();
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

    public void isExist(String id) {
        if (facultyExamDAO.get(id) == null) {
            throw new NotFoundException(loc.getString("faculty_exam_is_absent"));
        }
    }

    public void isFacultyExamNameUnique(FacultyExam facultyExam) {
        List<FacultyExam> facultyExams = new ArrayList<>();
        facultyExams.add(facultyExam);
        isFacultyExamNamesUnique(facultyExams, facultyExam.getFacultyId());
    }

    public void isFacultyExamNamesUnique(List<FacultyExam> facultyExams, String facultyId) {
        Map<Integer, String> names = new HashMap<>();
        facultyExamDAO.facultyExamNames(names, facultyId);
        for (FacultyExam facultyExam : facultyExams) {
            int id = Integer.parseInt(facultyExam.getId());
            String name = facultyExam.getExam();
            if (names.values().contains(name) && !name.equals(names.get(id))) {
                throw new ParamException.FormParamException(new RuntimeException(loc.getString("non_unique_faculty_exam_name")), "Name", "");
            } else {
                names.put(id, name);
            }
        }
    }
}
