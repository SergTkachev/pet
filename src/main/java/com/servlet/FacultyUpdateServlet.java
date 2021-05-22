package com.servlet;

import com.model.Faculty;
import com.model.FacultyExam;
import com.service.FacultyExamService;
import com.service.FacultyService;
import com.sun.jersey.api.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacultyUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("facultyUpdateServletWasSubmitted");
        req.getSession().removeAttribute("faculties");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        try {
            String id = req.getParameter("selected_id");
            if (req.getAttribute("err") == null) {
                req.getSession().setAttribute("faculty", FacultyService.getInstance().get(id));
            }
            getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/faculty/update.jsp").forward(req, resp);
        } catch (NotFoundException ex) {
            req.getSession().setAttribute("err", ex.getResponse().getEntity().toString());
            resp.sendRedirect("/facultylist");
        } catch (WebApplicationException ex) {
            req.getSession().setAttribute("err", ex.getCause().getMessage());
            resp.sendRedirect("/facultylist");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("facultyUpdateServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("facultyUpdateServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        Faculty faculty = (Faculty) req.getSession().getAttribute("faculty");
        String facultyId = faculty.getId();
        List<FacultyExam> fullList = faculty.getExams();
        List<FacultyExam> updateList = new ArrayList<>();
        int i = 0;
        while (req.getParameter(String.valueOf(i)) != null) {
            String id = req.getParameter(String.valueOf(i++));
            String name = req.getParameter("name" + id);
            for (FacultyExam facultyExam : fullList) {
                if (id.equals(facultyExam.getId())) {
                    facultyExam.setExam(name);
                    break;
                }
            }
            if ("on".equalsIgnoreCase(req.getParameter("checkbox" + id))) {
                updateList.add(new FacultyExam(id, facultyId, name));
            }
        }
        try {
            FacultyExamService.getInstance().updateList(updateList, facultyId);
            req.getSession().removeAttribute("faculty");
            resp.sendRedirect("/facultylist");
        } catch (WebApplicationException ex) {
            req.getSession().setAttribute("err", ex.getCause().getMessage());
            resp.sendRedirect("/facultyupdate?selected_id=" + faculty.getId());
        }
    }
}
