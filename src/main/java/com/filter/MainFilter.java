package com.filter;

import com.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainFilter implements Filter {
    private static final String ROOT = "/";
    private static final String LOGIN = "/login";
    private static final String LOCALIZATION = "/localization";
    private static final String SIGN_UP = "/signup";

    private static final String FACULTY_SORT = "/facultysort";
    private static final String SCAN_DOWNLOAD = "/scandownload";

    private static final String ADMIN = "/admin";
    private static final String FACULTY_CREATE = "/facultycreate";
    private static final String FACULTY_LIST = "/facultylist";
    private static final String FACULTY_UPDATE = "/facultyupdate";
    private static final String FACULTY_DELETE = "/facultydelete";
    private static final String FACULTY_EXAM_CREATE = "/facultyexamcreate";
    private static final String FACULTY_EXAM_DELETE = "/facultyexamdelete";
    private static final String STUDENT_LIST = "/studentlist";
    private static final String SCAN_REVIEW = "/scanreview";
    private static final String STUDENT_BLOCK_UNBLOCK = "/studentblockunblock";
    private static final String SHEET = "/sheet";

    private static final String REGISTRATION_LIST = "/registrationlist";
    private static final String REGISTRATION = "/registration";
    private static final String SCAN_UPLOAD = "/scanupload";

    private static final String[] guestPages = {
            ROOT,
            LOGIN,
            LOCALIZATION,
            SIGN_UP
    };

    private static final String[] adminPages = {
            ROOT,
            LOGIN,
            LOCALIZATION,
            SIGN_UP,
            FACULTY_SORT,
            ADMIN,
            FACULTY_CREATE,
            FACULTY_LIST,
            FACULTY_UPDATE,
            FACULTY_DELETE,
            FACULTY_EXAM_CREATE,
            FACULTY_EXAM_DELETE,
            STUDENT_LIST,
            SCAN_REVIEW,
            SCAN_DOWNLOAD,
            STUDENT_BLOCK_UNBLOCK,
            SHEET
    };

    private static final String[] studentPages = {
            ROOT,
            LOGIN,
            LOCALIZATION,
            SIGN_UP,
            SCAN_UPLOAD,
            SCAN_DOWNLOAD,
            FACULTY_SORT,
            REGISTRATION_LIST,
            REGISTRATION
    };

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = ((HttpServletRequest) request).getRequestURI().toLowerCase();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = (User) httpRequest.getSession().getAttribute("user");
        List<String> allowedPages = Arrays.asList(guestPages);
        if (user != null && Integer.valueOf(user.getId()) > 0) {
            allowedPages = Arrays.asList(user.isAdmin() ? adminPages : studentPages);
        }
        if (allowedPages.contains(uri)) {
            chain.doFilter(request, response);
        } else {
            httpRequest.getRequestDispatcher(httpRequest.getSession().getServletContext().getContextPath() + "/forbidden.jsp").forward(httpRequest, httpResponse);
        }
    }

    public void destroy() {
    }
}