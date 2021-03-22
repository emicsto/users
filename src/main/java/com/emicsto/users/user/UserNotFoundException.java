package com.emicsto.users.user;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3309294183745221148L;

    public UserNotFoundException(String login) {
        super(String.format("Could not find user with login %s", login));
    }
}
