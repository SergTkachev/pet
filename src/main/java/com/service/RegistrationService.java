package com.service;

import com.dao.RegistrationDAO;
import com.model.Faculty;
import com.validation.RegistrationValidator;

public class RegistrationService {
    private static RegistrationService instance;
    private RegistrationDAO registrationDAO;
    private RegistrationValidator registrationValidator;

    private RegistrationService() {
        registrationDAO = RegistrationDAO.getInstance();
        registrationValidator = RegistrationValidator.getInstance();
    }

    public static synchronized RegistrationService getInstance() {
        if (instance == null) {
            instance = new RegistrationService();
        }
        return instance;
    }

    public void createRegistration(String studentId, Faculty faculty) {
        registrationValidator.isMarksValid(faculty.getExams());
        registrationDAO.insertRegistrationWithMarks(studentId, faculty);
    }
}
