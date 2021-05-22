package com.servlet;

import com.model.User;
import com.service.UserService;
import com.util.Utf8Control;
import com.validation.Validator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PropertyConfigurator.configure(getServletContext().getRealPath("/")
                + getInitParameter("log4j"));
        Logger rootLogger = Logger.getRootLogger();
        getServletContext().setAttribute("log4j", rootLogger);
        rootLogger.info("Starting LoginServlet as first servlet...");

        ResourceBundle loc = ResourceBundle.getBundle("i18n.messages",
                new Locale("en"),
                new Utf8Control());
        config.getServletContext().setAttribute("loc", loc);
        Validator.loc = loc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("loginServletWasSubmitted");
        if (req.getSession().getAttribute("err") != null) {
            req.setAttribute("err", req.getSession().getAttribute("err"));
            req.getSession().removeAttribute("err");
        }
        req.getSession().removeAttribute("user");
        getServletContext().getRequestDispatcher(getServletContext().getContextPath() + "/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("loginServletWasSubmitted") != null) {
            doGet(req, resp);
            return;
        }
        req.getSession().setAttribute("loginServletWasSubmitted", true);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        try {
            UserService.getInstance().initialAdminPasswordSetting();
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            UserService.getInstance().authenticate(email, password);
            User user = UserService.getInstance().identificate(email);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(user.isAdmin() ? "/admin" : "/registrationlist");
        } catch (WebApplicationException ex) {
            req.setAttribute("err", ex.getCause().getMessage());
            doGet(req, resp);
        }
    }
}
