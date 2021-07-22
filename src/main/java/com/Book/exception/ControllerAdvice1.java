package com.Book.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvice1  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(TitleAllreadyPresent.class)
	public ResponseEntity<String> titleallreadrexist(TitleAllreadyPresent titleallreadrpresent){
		return new ResponseEntity<String>("Given Title is Allready present in Database", HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Invalidbookdetails.class)
	public ResponseEntity<String> bookdetailsnotvalied(Invalidbookdetails invalidbookdetails){
		return new ResponseEntity<String>("Input title Field ", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoRecordFound.class)
	public ResponseEntity<String> noRecord(NoRecordFound norecordfound){
		return new ResponseEntity<String>("No Records in Database", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvaliedTitle.class)
	public  ResponseEntity<String> titlrNotValid(InvaliedTitle invalidtitle){
		return new ResponseEntity<String>("Given a valied title", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> parameterisnotpresent(NoSuchElementException nosuchelementexception){
		return new ResponseEntity<String>("Given a valied id and a body", HttpStatus.BAD_REQUEST);
	}
	

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>("Plse change the request type", HttpStatus.BAD_REQUEST);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>("Plse Given a valied body", HttpStatus.BAD_REQUEST);
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			org.springframework.web.bind.MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		return new ResponseEntity<Object>( ex.getObjectName() +"is not null"+ex.getMessage() , HttpStatus.BAD_REQUEST);
		
	}
}
