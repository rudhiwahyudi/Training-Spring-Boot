package com.Bootcamp19.RestFullAPI.Exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String username) {
        super("User with name " +username+ "allready exist" );
    }
}
