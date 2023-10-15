package com.ecom.user;

import java.io.Serializable;

public class CustomNotFoundException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    public CustomNotFoundException(String message) {
        super(message);
    }
}