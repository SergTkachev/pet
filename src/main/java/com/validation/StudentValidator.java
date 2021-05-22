package com.validation;

import com.dao.UserDAO;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.ParamException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentValidator extends Validator {
    public static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static StudentValidator instance;
    private UserDAO userDAO;

    private StudentValidator() {
        userDAO = UserDAO.getInstance();
    }

    public static synchronized StudentValidator getInstance() {
        if (instance == null) {
            instance = new StudentValidator();
        }
        return instance;
    }

    public void isEmailNullOrEmpty(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("empty_email")), "Email", "");
        }
    }

    public void isPasswordNullOrEmpty(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("empty_password")), "Password", "");
        }
    }

    public void isFirstNameNullOrEmpty(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("empty_first_name")), "FirstName", "");
        }
    }

    public void isLastNameNullOrEmpty(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("empty_last_name")), "LastName", "");
        }
    }

    public void isExists(String id) {
        if (userDAO.get(id) == null) {
            throw new NotFoundException(loc.getString("user_is_absent"));
        }
    }

    public void isEmailValid(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        if (!matcher.find()) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_email")), "Email", "");
        }
    }

    public void isEmailNotUnique(String email) {
        if (userDAO.userExists(email)) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("non_unique_email")), "Email", "");
        }
    }

    public void authenticate(String email, String password) {
        if (!userDAO.authenticate(email, password)) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_email_password")), "Email/password", "");
        }
    }
}
