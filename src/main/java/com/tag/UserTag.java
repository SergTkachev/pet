package com.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserTag extends TagSupport {

    private String firstName;
    private String lastName;

    @Override
    public int doStartTag() throws JspException {
        try {
            StringBuilder fullName = new StringBuilder();
            if (firstName != null && !firstName.trim().isEmpty()) {
                fullName.append(firstName.trim());
            }
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            if (lastName != null && !lastName.trim().isEmpty()) {
                fullName.append(lastName.trim());
            }
            pageContext.getOut().write(fullName.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        super.release();
        this.firstName = null;
        this.lastName = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
