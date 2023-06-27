package com.example.cms.domain.admingroup.service;

public class AdminGroupInUseException extends RuntimeException {

    public AdminGroupInUseException(String message) {
        super(message);
    }
}
