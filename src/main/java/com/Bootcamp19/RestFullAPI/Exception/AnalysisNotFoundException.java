package com.Bootcamp19.RestFullAPI.Exception;

public class AnalysisNotFoundException extends RuntimeException {
    public AnalysisNotFoundException(Long id) {
        super("Data dengan id "+ id +" tidak ditemukan ");
    }
}
