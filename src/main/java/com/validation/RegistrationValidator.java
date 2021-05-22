package com.validation;

import com.model.FacultyExam;
import com.sun.jersey.api.ParamException;

import java.util.List;

public class RegistrationValidator extends Validator {
    private static RegistrationValidator instance;

    private RegistrationValidator() {

    }

    public static synchronized RegistrationValidator getInstance() {
        if (instance == null) {
            instance = new RegistrationValidator();
        }
        return instance;
    }

    public void isMarksValid(List<FacultyExam> exams) {
        for (FacultyExam exam : exams) {
            String markValue = exam.getExammark();
            if (isIntegerValid(markValue)
                    || Integer.valueOf(markValue) > 12) {
                throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_mark")), "Mark", "1");
            }
        }
    }
}
