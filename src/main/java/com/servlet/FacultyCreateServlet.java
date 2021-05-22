package com.servlet;

import com.model.Faculty;
import com.service.FacultyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;

public class FacultyCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("facultyCreateServletWasSubmitted");
        req.getSession().removeAttribute("faculties");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        if (req.getAttribute("err") == null) {
            req.getSession().setAttribute("faculty", new Faculty("-1", "", "", ""));
        }
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/faculty/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("facultyCreateServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("facultyCreateServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        try {
            Faculty faculty = (Faculty) req.getSession().getAttribute("faculty");
            faculty.setName(req.getParameter("name"));
            faculty.setBudget(req.getParameter("budget"));
            faculty.setCommon(req.getParameter("common"));
            FacultyService.getInstance().add(faculty);
            req.getSession().removeAttribute("faculty");
            resp.sendRedirect("/admin");
        } catch (WebApplicationException ex) {
            req.setAttribute("err", ex.getCause().getMessage());
            doGet(req, resp);
        }
    }
}
