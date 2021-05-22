package com.service;

import com.dao.UserDAO;
import com.model.Mark;
import com.model.User;
import com.validation.StudentMarkValidator;
import com.validation.StudentValidator;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.OptionalDouble;

public class UserService {
    private static UserService instance;
    private UserDAO userDAO;
    private StudentValidator studentValidator;
    private StudentMarkValidator studentMarkValidator;

    private UserService() {
        userDAO = UserDAO.getInstance();
        studentValidator = StudentValidator.getInstance();
        studentMarkValidator = StudentMarkValidator.getInstance();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private String encryptPassword(String password) {
        String encryptedPassword = null;
        try {
            String strkey = "MY KEY";
            SecretKeySpec key = new SecretKeySpec(strkey.getBytes("UTF-8"), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            if (cipher == null) {
                throw new Exception("Invalid key or cypher");
            }
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encryptedPassword = new String(cipher.doFinal(password.getBytes("UTF-8")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encryptedPassword;
    }

    public void authenticate(String email, String password) {
        studentValidator.isEmailNullOrEmpty(email);
        studentValidator.isPasswordNullOrEmpty(password);
        studentValidator.isEmailValid(email);
        studentValidator.authenticate(email, encryptPassword(password));
    }

    public User identificate(String email) {
        User user = userDAO.indentificate(email);
        return user;
    }

    public void createStudent(User student, String password, List<Mark> marks) {
        studentValidator.isEmailNullOrEmpty(student.getEmail());
        studentValidator.isPasswordNullOrEmpty(password);
        studentValidator.isFirstNameNullOrEmpty(student.getFirstName());
        studentValidator.isLastNameNullOrEmpty(student.getLastName());
        studentMarkValidator.isNameNullOrEmpty(marks);
        studentMarkValidator.isMarksValid(marks);
        studentValidator.isEmailValid(student.getEmail());
        studentMarkValidator.isMarkNamesUnique(marks);
        studentValidator.isEmailNotUnique(student.getEmail());
        OptionalDouble optionalDoubleAverageMark = marks.stream().mapToInt(mark -> Integer.valueOf(mark.getMark())).average();
        optionalDoubleAverageMark.ifPresent(student::setAverageMark);
        userDAO.insertStudentWithMarks(student, encryptPassword(password), marks);
    }

    public List<User> getAll(String rootPath) {
        return userDAO.getAll(rootPath);
    }

    public void blockUnblockStudent(String id) {
        studentValidator.isExists(id);
        userDAO.blockUnblockStudent(id);
    }

    public void initialAdminPasswordSetting() {
        userDAO.initialAdminPasswordSetting(encryptPassword("admin"));
    }
}
