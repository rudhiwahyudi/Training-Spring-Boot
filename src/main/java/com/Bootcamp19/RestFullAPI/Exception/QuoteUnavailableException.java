package com.Bootcamp19.RestFullAPI.Exception;

public class QuoteUnavailableException extends RuntimeException {
    public QuoteUnavailableException(String message) {
        super(message);
    }
}
