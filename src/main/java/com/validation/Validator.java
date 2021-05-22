package com.validation;

import com.sun.jersey.api.ParamException;

import java.util.ResourceBundle;

public abstract class Validator {
    public static ResourceBundle loc;

    protected boolean isIntegerValid(String stringValue) {
        boolean exception = false;
        try {
            int intValue = Integer.parseInt(stringValue);
            if (intValue < 1) {
                exception = true;
            }
        } catch (NumberFormatException ex) {
            exception = true;
        }
        return exception;
    }

    public void isIdValid(String idString) {
        if (isIntegerValid(idString)) {
            throw new ParamException.FormParamException(new RuntimeException(loc.getString("incorrect_id")), "ID", "1");
        }
    }
}
