package edu.uoc.epcsd.showcatalog.errors.handler;


import edu.uoc.epcsd.showcatalog.errors.dto.ApiErrorDto;
import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidParamError.class)
    public ResponseEntity<ApiErrorDto> handleInvalidParamError(Exception cause, WebRequest request) {
        ApiErrorDto error = apiErrorOf(request, HttpStatus.BAD_REQUEST);
        error.setError(InvalidParamError.class.getName());
        error.setDescription(cause.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotUniqueValueError.class)
    public ResponseEntity<ApiErrorDto> handleNotUniqueValueError(Exception cause, WebRequest request) {
        ApiErrorDto error = apiErrorOf(request, HttpStatus.BAD_REQUEST);
        error.setError(NotUniqueValueError.class.getName());
        error.setDescription(cause.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ApiErrorDto apiErrorOf(WebRequest request, HttpStatus status) {
        ApiErrorDto error = new ApiErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatusCode(status.value());
        error.setStatusReasonPhrase(status.getReasonPhrase());
        error.setApiCall(request.getContextPath());
        return error;
    }
}
