package com.Bootcamp19.RestFullAPI.Exception;

public class AnalysisAlreadyExistsException extends RuntimeException {
    public AnalysisAlreadyExistsException(String kalimat) {
        super("Kalimat '" + kalimat + "' sudah ada");
    }
}
