package com.Bootcamp19.RestFullAPI.Exception;


import com.Bootcamp19.RestFullAPI.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuoteUnavailableException.class)
    public ResponseEntity<BaseResponse<Object>> handleQuoteUnavailableException(QuoteUnavailableException quoteUnavailableException){
        log.error("QuoteUnavailableException: {}", quoteUnavailableException.getMessage());
        return new ResponseEntity<>(BaseResponse.error(quoteUnavailableException.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException validException){
        List<String> message = new ArrayList<>();
        for (FieldError fieldError : validException.getBindingResult().getFieldErrors()){
            message.add(fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(BaseResponse.error(String.join(", ", message)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AnalysisNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFound(AnalysisNotFoundException exception) {
        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleQuoteNotFound(QuoteNotFoundException exception) {
        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnalysisAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<Object>> handleAlreadyExists(AnalysisAlreadyExistsException exception) {

        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Object>> handleBadRequest(IllegalArgumentException exception) {
        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ResponseEntity<>(BaseResponse.error("Parameter '" + exception.getName() + "' tidak valid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<Object>> handleMissingParameter(MissingServletRequestParameterException exception) {
        return new ResponseEntity<>(BaseResponse.error("Parameter '" + exception.getParameterName() + "' wajib diisi"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneral(Exception exception) {
        log.error("Unhandled exception", exception);
        return new ResponseEntity<>(BaseResponse.error("Terjadi kesalahan pada server"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
