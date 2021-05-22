package com.servlet;

import com.model.Faculty;
import com.model.FacultyExam;
import com.model.User;
import com.service.FacultyService;
import com.service.RegistrationService;
import com.sun.jersey.api.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;

public class RegistrationCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("registrationCreateServletWasSubmitted");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        try {
            String id = req.getParameter("selected_id");
            if (req.getAttribute("err") == null) {
                req.getSession().setAttribute("regfaculty", FacultyService.getInstance().get(id));
            }
            getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/registration/create.jsp").forward(req, resp);
        } catch (NotFoundException ex) {
            req.getSession().setAttribute("err", ex.getResponse().getEntity().toString());
            resp.sendRedirect("/registrationlist");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("registrationCreateServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("registrationCreateServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        try {
            Faculty faculty = (Faculty) req.getSession().getAttribute("regfaculty");
            int i = 0;
            while (req.getParameter(String.valueOf(i)) != null) {
                String id = req.getParameter(String.valueOf(i++));
                String exammark = req.getParameter("exammark" + id);
                for (FacultyExam exam : faculty.getExams()) {
                    if (id.equals(exam.getId())) {
                        exam.setExammark(exammark);
                        break;
                    }
                }
            }
            RegistrationService.getInstance().createRegistration(((User) req.getSession().getAttribute("user")).getId(), faculty);
            resp.sendRedirect("/registrationlist");
        } catch (WebApplicationException ex) {
            req.setAttribute("err", ex.getCause().getMessage());
            doGet(req, resp);
        }
    }
}
