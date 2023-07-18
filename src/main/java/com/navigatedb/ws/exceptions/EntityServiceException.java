package com.navigatedb.ws.exceptions;

public class EntityServiceException extends RuntimeException{

    private static final long serialVersionUID = 6542113866149089716L;

    public EntityServiceException(String message) {
        super(message);
    }
}
