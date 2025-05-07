package ru.home.exception;

public class UserAndSubscriptionAlreadyExistsException extends RuntimeException{
    public UserAndSubscriptionAlreadyExistsException(String message) {
        super(message);
    }
}
