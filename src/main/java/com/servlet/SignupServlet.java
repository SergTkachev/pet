package com.servlet;

import com.model.Mark;
import com.model.User;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("signupServletWasSubmitted");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        if (req.getAttribute("err") == null) {
            req.getSession().setAttribute("user", new User("-1", "", "", "", "", "", "", "", 0, false, false));
        }
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/student/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("signupServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("signupServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        try {
            User student = (User) req.getSession().getAttribute("user");
            student.setEmail(req.getParameter("email"));
            student.setFirstName(req.getParameter("first_name"));
            student.setMiddleName(req.getParameter("middle_name"));
            student.setLastName(req.getParameter("last_name"));
            student.setDistrict(req.getParameter("district"));
            student.setCity(req.getParameter("city"));
            student.setSchool(req.getParameter("school"));
            List<Mark> marks = new ArrayList<>();
            int i = 0;
            while (req.getParameter(String.valueOf("subject" + i)) != null) {
                String subject = req.getParameter("subject" + i);
                String mark = req.getParameter("mark" + i++);
                marks.add(new Mark("-1", "-1", subject, mark));
            }
            student.setMarks(marks);
            UserService.getInstance().createStudent(student, req.getParameter("password"), marks);
            resp.sendRedirect("/scanupload");
        } catch (WebApplicationException ex) {
            req.setAttribute("err", ex.getCause().getMessage());
            doGet(req, resp);
        }
    }
}
