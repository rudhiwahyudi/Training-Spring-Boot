package com.Bootcamp19.RestFullAPI.Exception;

public class QuoteNotFoundException extends RuntimeException {
    public QuoteNotFoundException(Long id) {
        super("Quote dengan id " + id + " tidak ditemukan");
    }
}
