package com.mitocode.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ModelNotFoundException.class)
    public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Model Not Found");
        problemDetail.setType(URI.create("/not-found"));
        return problemDetail;
    }

    /*	@ExceptionHandler(ModelNotFoundException.class) //Esta version equivale  pero sin problemDetail
	public ErrorResponse handleModelNotFoundException(ModelNotFoundException ex, WebRequest req){
		return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
				.title("Model not found")
				.type(URI.create(req.getContextPath()))
				.property("test", "value-test")
				.property("age", 32)
				.build();
	}*/

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<CustomErrorResponse> handleSQLException(SQLException ex, WebRequest req){
		CustomErrorResponse res = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(res, HttpStatus.CONFLICT);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String msg = ex.getBindingResult().getAllErrors().stream().map(e -> e.getCode().concat(":").concat(e.getDefaultMessage())).collect(Collectors.joining());
		CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorResponse> handleAllException(ModelNotFoundException ex, WebRequest request) {
		CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}