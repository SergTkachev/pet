package com.validation;

import com.model.Mark;
import com.sun.jersey.api.ParamException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentMarkValidator extends Validator {
    private static StudentMarkValidator instance;

    private StudentMarkValidator() {

    }

    public static synchronized StudentMarkValidator getInstance() {
        if (instance == null) {
            instance = new StudentMarkValidator();
        }
        return instance;
    }

    public void isNameNullOrEmpty(List<Mark> marks) {
        for (Mark mark : marks) {
            String name = mark.getSubject();
            if (name == null || name.trim().isEmpty()) {
                throw new ParamException.FormParamException(new RuntimeException(loc.getString("empty_mark_name")), "Mark Name", "");
            }
        }
    }

    public void isMarksValid(List<Mark> marks) {
        for (Mark mark : marks) {
            String markValue = mark.getMark();
            if (isIntegerValid(markValue)
                    || Integer.valueOf(markValue) > 12) {
                throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_mark")), "Mark", "1");
            }
        }
    }

    public void isMarkNamesUnique(List<Mark> marks) {
        Set<String> names = new HashSet<>();
        for (Mark mark : marks) {
            String name = mark.getSubject();
            if (names.contains(name)) {
                throw new ParamException.FormParamException(new RuntimeException(loc.getString("non_unique_faculty_name")), "Name", "");
            } else {
                names.add(name);
            }
        }
    }
}
