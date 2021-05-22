package com.servlet;

import com.model.FacultyExam;
import com.service.FacultyExamService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;

public class FacultyExamCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("facultyExamCreateServletWasSubmitted");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        if (req.getAttribute("err") == null) {
            req.getSession().setAttribute("facultyexam", new FacultyExam("-1", req.getParameter("faculty_id"), ""));
        }
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/facultyexam/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("facultyExamCreateServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("facultyExamCreateServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        try {
            FacultyExam facultyExam = (FacultyExam) req.getSession().getAttribute("facultyexam");
            facultyExam.setExam(req.getParameter("name"));
            FacultyExamService.getInstance().add(facultyExam);
            resp.sendRedirect("/facultyupdate?selected_id=" + facultyExam.getFacultyId());
        } catch (WebApplicationException ex) {
            req.setAttribute("err", ex.getCause().getMessage());
            doGet(req, resp);
        }
    }
}
