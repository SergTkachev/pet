package com.servlet;

import com.util.Utf8Control;
import com.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationServlet extends HttpServlet {
    private ResourceBundle.Control utf8Control = new Utf8Control();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("lang") != null) {
            ResourceBundle loc = ResourceBundle.getBundle("i18n.messages",
                    new Locale(req.getParameter("lang")),
                    utf8Control);
            req.getServletContext().setAttribute("loc", loc);
            Validator.loc = loc;
        }
        resp.sendRedirect("/login");
    }
}
