package com.servlet;

import com.model.Faculty;
import com.service.FacultyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacultyListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("facultyListServletWasSubmitted");
        req.getSession().removeAttribute("faculty");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        if (req.getAttribute("err") == null) {
            req.getSession().setAttribute("faculties", FacultyService.getInstance().getAll(
                    (String) req.getSession().getAttribute("fs_column"),
                    (String) req.getSession().getAttribute("fs_direction")));
        }
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/faculty/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("facultyListServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("facultyListServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        int listSize = Integer.valueOf(req.getParameter("listsize"));
        List<Faculty> fullList = (List<Faculty>) req.getSession().getAttribute("faculties");
        List<Faculty> updateList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            String id = req.getParameter(String.valueOf(i));
            String name = req.getParameter("name" + id);
            String budget = req.getParameter("budget" + id);
            String common = req.getParameter("common" + id);
            for (Faculty faculty : fullList) {
                if (id.equals(faculty.getId())) {
                    faculty.setName(name);
                    faculty.setBudget(budget);
                    faculty.setCommon(common);
                    break;
                }
            }
            if ("on".equalsIgnoreCase(req.getParameter("checkbox" + id))) {
                updateList.add(new Faculty(id, name, budget, common));
            }
        }
        try {
            FacultyService.getInstance().updateList(updateList);
            req.getSession().removeAttribute("faculties");
            resp.sendRedirect("/admin");
        } catch (WebApplicationException ex) {
            req.setAttribute("err", ex.getCause().getMessage());
            doGet(req, resp);
        }
    }
}
