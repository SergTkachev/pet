package com;

import com.dao.CommonDAO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTest {
    protected CommonDAO commonDAO = null;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        List<String> queries = new ArrayList<>();
        queries.add("DELETE FROM sheet;");
        queries.add("DELETE FROM user;");
        queries.add("DELETE FROM faculty;");
        queries.add("ALTER TABLE sheet AUTO_INCREMENT = 1;");
        queries.add("ALTER TABLE sheet_data AUTO_INCREMENT = 1;");
        queries.add("ALTER TABLE registration AUTO_INCREMENT = 1;");
        queries.add("ALTER TABLE exam_mark AUTO_INCREMENT = 1;");
        queries.add("ALTER TABLE user AUTO_INCREMENT = 1;");
        queries.add("ALTER TABLE student_mark AUTO_INCREMENT = 1;");
        queries.add("ALTER TABLE faculty AUTO_INCREMENT = 1;");
        queries.add("ALTER TABLE faculty_exam AUTO_INCREMENT = 1;");
        queries.add("INSERT INTO faculty(id, name, budget, common) VALUES (1, 'FirstFaculty', 1, 3);");
        queries.add("INSERT INTO faculty(id, name, budget, common) VALUES (2, 'SecondFaculty', 1, 2);");
        queries.add("INSERT INTO faculty(id, name, budget, common) VALUES (3, 'ThirdFaculty', 2, 3);");
        queries.add("INSERT INTO faculty(id, name, budget, common) VALUES (4, 'FourthFaculty', 5, 10);");
        queries.add("INSERT INTO faculty(id, name, budget, common) VALUES (5, 'FifthFaculty', 25, 30);");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (1, 1, 'FirstExam1');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (2, 1, 'FirstExam2');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (3, 2, 'SecondExam1');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (4, 2, 'SecondExam2');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (5, 3, 'ThirdExam1');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (6, 3, 'ThirdExam2');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (7, 4, 'FourthExam1');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (8, 4, 'FourthExam2');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (9, 5, 'FifthExam1');");
        queries.add("INSERT INTO faculty_exam(id, faculty_id, exam) VALUES (10, 5, 'FifthExam2');");
        queries.add("INSERT INTO user(id, email, password, first_name, last_name, average_mark, blocked, admin) "
                + "VALUES (1, 'admin@admin.com', 'admin', 'admin', 'admin', 0, false, true);");
        queries.add("INSERT INTO user(id, email, password, first_name, last_name, average_mark, blocked, admin) "
                + "VALUES (2, 'second@admin.com', 'second', 'second', 'second', 10.5, false, false);");
        queries.add("INSERT INTO user(id, email, password, first_name, last_name, average_mark, blocked, admin) "
                + "VALUES (3, 'third@third.com', 'third', 'third', 'third', 8.5, false, false);");
        queries.add("INSERT INTO user(id, email, password, first_name, last_name, average_mark, blocked, admin) "
                + "VALUES (4, 'fourth@fourth.com', 'fourth', 'fourth', 'fourth', 11.5, false, false);");
        queries.add("INSERT INTO user(id, email, password, first_name, last_name, average_mark, blocked, admin) "
                + "VALUES (5, 'fifth@fifth.com', 'fifth', 'fifth', 'fifth', 7, false, false);");
        queries.add("INSERT INTO user(id, email, password, first_name, last_name, average_mark, blocked, admin) "
                + "VALUES (6, 'sixth@sixth.com', 'sixth', 'sixth', 'sixth', 9.5, false, false);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (1, 2, 'FirstMark1', 11);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (2, 2, 'FirstMark2', 10);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (3, 3, 'SecondMark1', 8);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (4, 3, 'SecondMark2', 9);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (5, 4, 'ThirdMark1', 12);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (6, 4, 'ThirdMark2', 11);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (7, 5, 'FourthMark1', 5);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (8, 5, 'FourthMark2', 9);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (9, 6, 'FifthMark1', 10);");
        queries.add("INSERT INTO student_mark(id, student_id, subject, mark) VALUES (10, 6, 'FifthMark2', 9);");
        queries.add("INSERT INTO registration(id, faculty_id, student_id) VALUES (1, 1, 2);");
        queries.add("INSERT INTO registration(id, faculty_id, student_id) VALUES (2, 1, 3);");
        queries.add("INSERT INTO registration(id, faculty_id, student_id) VALUES (3, 1, 4);");
        queries.add("INSERT INTO registration(id, faculty_id, student_id) VALUES (4, 1, 5);");
        queries.add("INSERT INTO registration(id, faculty_id, student_id) VALUES (5, 1, 6);");
        queries.add("INSERT INTO registration(id, faculty_id, student_id) VALUES (6, 2, 2);");
        queries.add("INSERT INTO registration(id, faculty_id, student_id) VALUES (7, 2, 3);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (1, 1, 1, 11);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (2, 1, 2, 2);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (3, 2, 1, 8);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (4, 2, 2, 8);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (5, 3, 1, 9);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (6, 3, 2, 7);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (7, 4, 1, 2);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (8, 4, 2, 5);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (9, 5, 1, 6);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (10, 5, 2, 10);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (11, 6, 3, 11);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (12, 6, 4, 12);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (13, 7, 3, 5);");
        queries.add("INSERT INTO exam_mark(id, registration_id, faculty_exam_id, mark) VALUES (14, 7, 4, 6);");
        queries.add("INSERT INTO sheet(id) VALUES (1);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (1, 1, 1, 2, 1, 11);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (2, 1, 1, 2, 2, 2); ");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (3, 1, 1, 3, 1, 8);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (4, 1, 1, 3, 2, 8); ");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (5, 1, 1, 4, 1, 9);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (6, 1, 1, 4, 2, 7); ");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (7, 1, 1, 5, 1, 2);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (8, 1, 1, 5, 2, 5); ");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (9, 1, 1, 6, 1, 6);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (10, 1, 1, 6, 2, 10); ");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (11, 1, 2, 2, 3, 11);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (12, 1, 2, 2, 4, 12); ");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (13, 1, 2, 3, 3, 5);");
        queries.add("INSERT INTO sheet_data(id, sheet_id, faculty_id, student_id, faculty_exam_id, mark) "
                + "VALUES (14, 1, 2, 3, 4, 6); ");

        try (Connection connection = commonDAO.getConnection()) {
            for (String query : queries) {
                connection.prepareStatement(query).execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
