package com.servlet;

import com.model.User;
import com.service.SheetService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class SheetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        NavigableMap<String, NavigableSet<User>> budgetMap = new TreeMap<>();
        NavigableMap<String, NavigableSet<User>> contractMap = new TreeMap<>();
        SheetService.getInstance().createSheet(budgetMap, contractMap);
        List<NavigableMap<String, NavigableSet<User>>> admittedUsers = new ArrayList();
        admittedUsers.add(budgetMap);
        admittedUsers.add(contractMap);
        req.getSession().setAttribute("admitted_users", admittedUsers);
        req.setAttribute("budget", budgetMap);
        req.setAttribute("contract", contractMap);
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/sheet/sheet.jsp").forward(req, resp);
    }
}
