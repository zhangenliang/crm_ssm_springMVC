package com.jhuniversity.crm.exception;

public class LoginException extends Exception {
    private String login;
    public LoginException() {
        super();
    }

    public LoginException(String login) {
        super(login);
    }


}
