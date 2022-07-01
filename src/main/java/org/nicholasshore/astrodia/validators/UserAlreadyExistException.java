package org.nicholasshore.astrodia.validators;

public class UserAlreadyExistException extends Exception{

    private String code = "DuplicateUserNotAllowed";

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public UserAlreadyExistException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}