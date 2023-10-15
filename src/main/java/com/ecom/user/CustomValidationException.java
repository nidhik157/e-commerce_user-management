package com.ecom.user;

import java.io.Serializable;

public class CustomValidationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    public CustomValidationException(String message) {
        super(message);
    }
}
